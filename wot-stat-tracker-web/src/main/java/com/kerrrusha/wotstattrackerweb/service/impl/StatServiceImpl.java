package com.kerrrusha.wotstattrackerweb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import com.kerrrusha.wotstattrackerdomain.entity.BaseEntity;
import com.kerrrusha.wotstattrackerdomain.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerweb.mapper.PlayerRequestMapper;
import com.kerrrusha.wotstattrackerweb.mapper.StatGraphMapper;
import com.kerrrusha.wotstattrackerweb.mapper.StatMapper;
import com.kerrrusha.wotstattrackerweb.service.StatService;
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
import java.util.stream.Collectors;

import static com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto.createDeltas;
import static com.kerrrusha.wotstattrackerweb.util.MappingUtil.toLocalDate;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final PlayerRequestMapper playerRequestMapper;
    private final StatGraphMapper statGraphMapper;
    private final StatMapper statMapper;

    @Value("${stat.amount}")
    private Integer amountOfRecentStatsToLoad;

    @Value("${activemq.queue.players-to-update}")
    private String playersQueueName;

    @Value("${allowed.data.update.every.hours}")
    private Integer allowedDataUpdateEveryHours;

    @Value("${stats.be.shown.on.graph.amount}")
    private Integer statsBeShownOnGraphAmount;

    @Override
    public List<Stat> findMostRecentByPlayer(PlayerRequestDto playerRequestDto) {
        Pageable pageable = PageRequest.of(0, amountOfRecentStatsToLoad);
        List<Stat> rawStats = statRepository.findDistinctByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(
                playerRequestDto.getNickname(),
                playerRequestDto.getRegion(),
                pageable
        );
        log.debug("Found {} of {} stats requested to {} player", rawStats.size(), amountOfRecentStatsToLoad, playerRequestDto);

        List<Stat> uniqueReversedStats = rawStats.stream()
                .distinct()
                .sorted(Comparator.comparing(Stat::getCreatedAt).reversed())
                .toList();
        log.debug("Unique stats size for {}-{} player: {}", playerRequestDto.getRegion(), playerRequestDto.getNickname(), uniqueReversedStats.size());
        return uniqueReversedStats;
    }

    @Override
    public Stat findCurrentStatByPlayer(PlayerRequestDto playerRequestDto) {
        return statRepository.findFirstByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(
                playerRequestDto.getNickname(),
                playerRequestDto.getRegion()
        );
    }

    @Override
    public Optional<Stat> findPreviousStatByPlayer(PlayerRequestDto playerRequestDto) {
        List<Stat> playerStats = findMostRecentByPlayer(playerRequestDto);
        return playerStats.size() > 1 ? Optional.of(playerStats.get(1)) : Optional.empty();
    }

    @Override
    public Optional<StatDeltaResponseDto> getDeltas(Stat playerCurrentStat) {
        PlayerRequestDto playerRequestDto = playerRequestMapper.mapToDto(playerCurrentStat.getPlayer());
        Optional<Stat> playerPreviousStatOptional = findPreviousStatByPlayer(playerRequestDto);
        return playerPreviousStatOptional.map(stat -> createDeltas(playerCurrentStat, stat));
    }

    @Override
    public StatGraphsResponseDto getStatGraphs(PlayerRequestDto playerRequestDto) {
        Map<LocalDate, Stat> dateToStatsUnique = findMostRecentByPlayer(playerRequestDto).stream()
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
    public void updateDataIfOutdated(PlayerRequestDto playerDto) {
        Stat currentStat = findCurrentStatByPlayer(playerDto);
        if (dataIsUpToDate(currentStat)) {
            log.info("Stat is up to date for: {}", playerDto);
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

    @Override
    public List<StatResponseDto> findMostRecent() {
        return statRepository.findTop15ByOrderByCreatedAtDesc().stream()
                .map(statMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private void sendForCollectingNewData(PlayerRequestDto playerDto) {
        log.info("Requesting stat update for: {}", playerDto);
        jmsTemplate.convertAndSend(playersQueueName, objectMapper.writeValueAsString(playerDto));
    }
    
}
