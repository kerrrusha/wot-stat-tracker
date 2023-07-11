package com.kerrrusha.wotstattrackerprovider;

import com.kerrrusha.wotstattrackerprovider.provider.PlayerPersonalDataProvider;
import com.kerrrusha.wotstattrackerprovider.provider.PlayerStatProvider;
import lombok.RequiredArgsConstructor;
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

    @Value("${activemq.queue.stat}")
    private String statQueueName;

    @JmsListener(destination = "${activemq.queue.players}")
    public void receivePlayersToCollectDataFor(String playerJson) {
        log.info("Received player to collect data for: {}", playerJson);
    }

}
