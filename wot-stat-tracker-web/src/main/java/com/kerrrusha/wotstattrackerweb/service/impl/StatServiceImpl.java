package com.kerrrusha.wotstattrackerweb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.service.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto.createDeltas;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final StatRepository statRepository;
    private final PlayerMapper playerMapper;

    @Value("${stat.amount}")
    private Integer amountOfRecentStatsToLoad;

    @Value("${activemq.queue.players}")
    private String playersQueueName;

    @Value("${allowed.data.update.every.hours}")
    private Integer allowedDataUpdateEveryHours;

    @Override
    public List<Stat> findMostRecentByNickname(String nickname) {
        Pageable pageable = PageRequest.of(0, amountOfRecentStatsToLoad);
        List<Stat> rawStats = statRepository.findDistinctByPlayer_NicknameLikeOrderByCreatedAtDesc(nickname, pageable);
        return rawStats.stream()
                .distinct()
                .sorted(Comparator.comparing(Stat::getCreatedAt).reversed())
                .toList();
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
    public void updateDataIfOutdated(Player player) {
        Stat currentStat = findCurrentStatByNickname(player.getNickname());
        if (dataIsUpToDate(currentStat)) {
            log.info("Stat is up to date for player: {}", player.getNickname());
            return;
        }
        sendForCollectingNewData(player);
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
    private void sendForCollectingNewData(Player player) {
        log.info("Requesting stat update for player: {}", player.getNickname());
        PlayerResponseDto playerDto = playerMapper.mapToDto(player);
        jmsTemplate.convertAndSend(playersQueueName, objectMapper.writeValueAsString(playerDto));
    }

}
