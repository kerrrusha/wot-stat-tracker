package com.kerrrusha.wotstattrackerprovider.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerInfoDto;
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
public class PlayerInfoUpdateListener {

    private final WargamingPlayerInfoProvider wargamingPlayerInfoProvider;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${activemq.queue.collected-player-info}")
    private String collectedPlayerInfoQueueName;

    @SneakyThrows
    @JmsListener(destination = "${activemq.queue.players-to-collect-player-info}")
    public void receivePlayersToCollectDataFor(String nickname) {
        log.info("Received player to collect info for: {}", nickname);
        WargamingPlayerInfoDto responseDto = wargamingPlayerInfoProvider.findByNickname(nickname);
        if (responseDto.isEmpty()) {
            log.warn("There are no such in-game player: {}", nickname);
        } else {
            sendCollectedData(responseDto);
        }
    }

    @SneakyThrows
    private void sendCollectedData(WargamingPlayerInfoDto responseDto) {
        String jsonResult = objectMapper.writeValueAsString(responseDto);
        log.info("Sending to {} collected data: {}", collectedPlayerInfoQueueName, jsonResult);
        jmsTemplate.convertAndSend(collectedPlayerInfoQueueName, jsonResult);
    }

}
