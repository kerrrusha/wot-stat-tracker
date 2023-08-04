package com.kerrrusha.wotstattrackerdata.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdata.dto.PlayerDto;
import com.kerrrusha.wotstattrackerdata.dto.StatDto;
import com.kerrrusha.wotstattrackerdata.dto.mapper.PlayerMapper;
import com.kerrrusha.wotstattrackerdata.dto.mapper.StatMapper;
import com.kerrrusha.wotstattrackerdata.entity.Stat;
import com.kerrrusha.wotstattrackerdata.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerdata.repository.StatRepository;
import com.kerrrusha.wotstattrackerdata.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatDataUpdateTrigger {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;
    private final PlayerMapper playerMapper;
    private final StatMapper statMapper;

    private final PlayerRepository playerRepository;
    private final StatRepository statRepository;

    @Value("${activemq.queue.players-to-update}")
    private String playersQueueName;

    @Deprecated(reason = "Continuous data update is meaningless, getting many duplicates")
    //@Scheduled(fixedRateString = "${dataupdate.every.milliseconds}")
    public void startDataUpdate() {
        log.info("Data update started...");

        List<Player> players = playerRepository.findAll();
        log.info("Found {} players, sending them for new data collecting...", players.size());

        players.forEach(this::sendForCollectingNewData);

        log.info("Players been sent successfully.");
    }

    @interface Deprecated {
        String reason();
    }

    @SneakyThrows
    private void sendForCollectingNewData(Player player) {
        PlayerDto playerDto = playerMapper.map(player);
        jmsTemplate.convertAndSend(playersQueueName, objectMapper.writeValueAsString(playerDto));
    }

    @SneakyThrows
    @JmsListener(destination = "${activemq.queue.collected-stat}")
    public void receiveCollectedStat(String statJson) {
        log.debug("Received new stat: {}", statJson);

        StatDto statDto = objectMapper.readValue(statJson, StatDto.class);
        Optional<Player> playerOptional = playerRepository.findByAccountId(statDto.getAccountId());
        if (playerOptional.isEmpty()) {
            throw new RuntimeException("Player do not exists by given account_id: " + statDto.getAccountId());
        }

        Player player = playerOptional.get();
        Stat statToSave = statMapper.map(statDto);
        statToSave.setPlayer(player);
        statToSave.setCreatedAt(LocalDateTime.now());
        statRepository.save(statToSave);

        log.info("Successfully collected stat data for {}", player.getNickname());
    }

}