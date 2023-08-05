package com.kerrrusha.wotstattrackerprovider.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.kerrrusha.wotstattrackerprovider.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerprovider.mapper.activemq.StatMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.modxvm.ModXvmStatDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.wotlife.WotLifePlayerStatDto;
import com.kerrrusha.wotstattrackerprovider.provider.modxvm.ModXvmStatProvider;
import com.kerrrusha.wotstattrackerprovider.provider.wargaming.WargamingPlayerPersonalDataProvider;
import com.kerrrusha.wotstattrackerprovider.provider.wotlife.WotLifePlayerStatProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatDataUpdateListener {

    private final WargamingPlayerPersonalDataProvider wargamingPlayerPersonalDataProvider;
    private final WotLifePlayerStatProvider wotLifePlayerStatProvider;
    private final ModXvmStatProvider modXvmStatProvider;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final StatMapper statMapper;

    private final Cache<String, Timestamp> dataUpdateCache;

    @Value("${activemq.queue.collected-stat}")
    private String statQueueName;

    @Value("${requests.caching}")
    private boolean doRequestsCaching;

    @SneakyThrows
    @JmsListener(destination = "${activemq.queue.players-to-update}")
    public void receivePlayersToCollectDataFor(String playerRequestDtoJson) {
        log.info("Received player to collect stat data for: {}", playerRequestDtoJson);
        PlayerRequestDto playerRequestDto = objectMapper.readValue(playerRequestDtoJson, PlayerRequestDto.class);

        if (doRequestsCaching && cacheContains(playerRequestDto.getAccountId())) {
            Timestamp previousRequestTimestamp = dataUpdateCache.getIfPresent(playerRequestDto.getAccountId());
            log.warn("Player data update rejected - previous request already made at {}", previousRequestTimestamp);
            return;
        } else {
            Timestamp now = Timestamp.from(Instant.now());
            log.debug("Caching player data update event with timestamp: {}", now);
            dataUpdateCache.put(playerRequestDto.getAccountId(), now);
        }

        WargamingPlayerPersonalDataDto playerPersonalDataDto = wargamingPlayerPersonalDataProvider.findByAccountId(playerRequestDto.getAccountId());
        WotLifePlayerStatDto playerStatDto = wotLifePlayerStatProvider.findByNickname(playerRequestDto.getNickname());
        ModXvmStatDto modXvmStatDto = modXvmStatProvider.findByAccountId(playerRequestDto.getAccountId());

        StatResponseDto statResponseDto = statMapper.map(playerPersonalDataDto, playerStatDto, modXvmStatDto);
        sendCollectedData(statResponseDto);
    }

    private boolean cacheContains(String key) {
        return nonNull(dataUpdateCache.getIfPresent(key));
    }

    @SneakyThrows
    private void sendCollectedData(StatResponseDto statResponseDto) {
        String jsonResult = objectMapper.writeValueAsString(statResponseDto);
        log.info("Sending to {} collected data: {}", statQueueName, jsonResult);
        jmsTemplate.convertAndSend(statQueueName, jsonResult);
    }

}
