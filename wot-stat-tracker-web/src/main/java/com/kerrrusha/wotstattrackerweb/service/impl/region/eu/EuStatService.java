package com.kerrrusha.wotstattrackerweb.service.impl.region.eu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.BaseEntity;
import com.kerrrusha.wotstattrackerweb.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatGraphMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto.createDeltas;
import static com.kerrrusha.wotstattrackerweb.util.MappingUtil.toLocalDate;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuStatService implements StatService {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final StatRepository statRepository;
    private final StatGraphMapper statGraphMapper;

    @Value("${stat.amount}")
    private Integer amountOfRecentStatsToLoad;

    @Value("${activemq.queue.players-to-update}")
    private String playersQueueName;

    @Value("${allowed.data.update.every.hours}")
    private Integer allowedDataUpdateEveryHours;

    @Value("${stats.be.shown.on.graph.amount}")
    private Integer statsBeShownOnGraphAmount;

    @Override
    public List<Stat> findMostRecentByNickname(String nickname) {
        Pageable pageable = PageRequest.of(0, amountOfRecentStatsToLoad);
        List<Stat> rawStats = statRepository.findDistinctByPlayer_NicknameLikeOrderByCreatedAtDesc(nickname, pageable);
        log.debug("Found {} of {} stats requested to {} player", rawStats.size(), amountOfRecentStatsToLoad, nickname);

        List<Stat> uniqueReversedStats = rawStats.stream()
                .distinct()
                .sorted(Comparator.comparing(Stat::getCreatedAt).reversed())
                .toList();
        log.debug("Unique stats size for {} player: {}", nickname, uniqueReversedStats.size());
        return uniqueReversedStats;
    }

    @Override
    public Stat findCurrentStatByNickname(String nickname) {
        return statRepository.findFirstByPlayer_NicknameLikeOrderByCreatedAtDesc(nickname);
    }

    @Override
    public Optional<Stat> findPreviousStatByNickname(String nickname) {
        List<Stat> playerStats = findMostRecentByNickname(nickname);
        return playerStats.size() > 1 ? Optional.of(playerStats.get(1)) : Optional.empty();
    }

    @Override
    public Optional<StatDeltaResponseDto> getDeltas(Stat playerCurrentStat) {
        Optional<Stat> playerPreviousStatOptional = findPreviousStatByNickname(playerCurrentStat.getPlayer().getNickname());
        return playerPreviousStatOptional.map(stat -> createDeltas(playerCurrentStat, stat));
    }

    @Override
    public StatGraphsResponseDto getStatGraphs(String nickname) {
        Map<LocalDate, Stat> dateToStatsUnique = findMostRecentByNickname(nickname).stream()
                .limit(statsBeShownOnGraphAmount + 1)
                .collect(toMap(stat -> toLocalDate(stat.getCreatedAt()), Function.identity(), (first, second) -> first, LinkedHashMap::new));
        log.debug("Found {} unique stat to date pairs", dateToStatsUnique.size());

        List<Stat> statsPerDaySorted = dateToStatsUnique.values()
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedAt))
                .toList();
        return statGraphMapper.mapToDto(statsPerDaySorted);
    }

    @Override
    public void updateDataIfOutdated(PlayerResponseDto playerDto) {
        Stat currentStat = findCurrentStatByNickname(playerDto.getNickname());
        if (dataIsUpToDate(currentStat)) {
            log.info("Stat is up to date for player: {}", playerDto.getNickname());
            return;
        }
        sendForCollectingNewData(playerDto);
    }

    @Override
    public boolean dataIsUpToDate(Stat currentStat) {
        if (isNull(currentStat)) {
            return false;
        }
        Duration difference = Duration.between(currentStat.getCreatedAt(), LocalDateTime.now());
        long hours = difference.toHours();
        return hours < allowedDataUpdateEveryHours;
    }

    @SneakyThrows
    private void sendForCollectingNewData(PlayerResponseDto playerDto) {
        log.info("Requesting stat update for player: {}", playerDto.getNickname());
        jmsTemplate.convertAndSend(playersQueueName, objectMapper.writeValueAsString(playerDto));
    }

}
