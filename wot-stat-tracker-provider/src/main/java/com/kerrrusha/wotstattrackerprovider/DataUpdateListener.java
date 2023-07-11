package com.kerrrusha.wotstattrackerprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.activemq.PlayerRequestDto;
import com.kerrrusha.wotstattrackerprovider.dto.activemq.StatResponseDto;
import com.kerrrusha.wotstattrackerprovider.dto.mapper.activemq.StatMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.dto.wotlife.WotLifePlayerStatDto;
import com.kerrrusha.wotstattrackerprovider.provider.PlayerPersonalDataProvider;
import com.kerrrusha.wotstattrackerprovider.provider.PlayerStatProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataUpdateListener {

    private final PlayerPersonalDataProvider playerPersonalDataProvider;
    private final PlayerStatProvider playerStatProvider;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final StatMapper statMapper;

    @Value("${activemq.queue.stat}")
    private String statQueueName;

    @SneakyThrows
    @JmsListener(destination = "${activemq.queue.players}")
    public void receivePlayersToCollectDataFor(String playerJson) {
        log.info("Received player to collect data for: {}", playerJson);
        PlayerRequestDto playerRequestDto = objectMapper.readValue(playerJson, PlayerRequestDto.class);

        WargamingPlayerPersonalDataDto playerPersonalDataDto = playerPersonalDataProvider.findByAccountId(playerRequestDto.getAccountId());
        WotLifePlayerStatDto playerStatDto = playerStatProvider.findByNickname(playerRequestDto.getNickname());

        StatResponseDto statResponseDto = statMapper.map(playerPersonalDataDto, playerStatDto);
        sendCollectedData(statResponseDto);
    }

    @SneakyThrows
    private void sendCollectedData(StatResponseDto statResponseDto) {
        String jsonResult = objectMapper.writeValueAsString(statResponseDto);
        log.info("Sending collected data: {}", jsonResult);
        jmsTemplate.convertAndSend(statQueueName, jsonResult);
    }

}
