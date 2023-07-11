package com.kerrrusha.wotstattrackerdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdata.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerdata.repository.StatRepository;
import com.kerrrusha.wotstattrackerdata.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataUpdateCommunicator {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    private final PlayerRepository playerRepository;
    private final StatRepository statRepository;

    @Value("${activemq.destination}")
    private String activemqDestination;

    @Scheduled(fixedRateString = "${dataupdate.every.milliseconds}")
    public void startDataUpdate() {
        log.info("Data update started...");

        List<Player> players = playerRepository.findAll();
        log.info("Found {} players, sending them for new data collecting...", players.size());

        players.forEach(this::sendForCollectingNewData);

        log.info("Data update finished.");
    }

    @SneakyThrows
    private void sendForCollectingNewData(Player player) {
        jmsTemplate.convertAndSend(activemqDestination, objectMapper.writeValueAsString(player));
    }

    @JmsListener(destination = "${activemq.destination}")
    public void receiveCollectedStat(String content) {
        log.info("Received message: {}", content);
    }

}
