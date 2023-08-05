package com.kerrrusha.wotstattrackerprovider.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerExistsDto;
import com.kerrrusha.wotstattrackerprovider.provider.wargaming.WargamingPlayerInfoProvider;
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
public class PlayerExistsListener {

    private final WargamingPlayerInfoProvider wargamingPlayerInfoProvider;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${activemq.queue.check-if-nickname-exists-results}")
    private String resultQueueName;

    @SneakyThrows
    @JmsListener(destination = "${activemq.queue.nicknames-to-check-if-exists}")
    public void receivePlayersToCollectDataFor(String playerRequestDtoJson) {
        log.info("Received player to check if exists: {}", playerRequestDtoJson);
        PlayerRequestDto playerRequestDto = objectMapper.readValue(playerRequestDtoJson, PlayerRequestDto.class);
        WargamingPlayerExistsDto responseDto = wargamingPlayerInfoProvider.getPlayerExists(playerRequestDto);
        sendCollectedData(responseDto);
    }

    @SneakyThrows
    private void sendCollectedData(WargamingPlayerExistsDto responseDto) {
        String jsonResult = objectMapper.writeValueAsString(responseDto);
        log.info("Sending to {} collected data: {}", resultQueueName, jsonResult);
        jmsTemplate.convertAndSend(resultQueueName, jsonResult);
    }

}
