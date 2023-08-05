package com.kerrrusha.wotstattrackerdata.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdata.dto.PlayerDto;
import com.kerrrusha.wotstattrackerdata.mapper.PlayerMapper;
import com.kerrrusha.wotstattrackerdata.entity.Player;
import com.kerrrusha.wotstattrackerdata.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerInfoUpdateListener {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @JmsListener(destination = "${activemq.queue.collected-player-info}")
    public void receiveCollectedPlayer(String playerJson) {
        log.debug("Received new player: {}", playerJson);

        PlayerDto playerDto = objectMapper.readValue(playerJson, PlayerDto.class);
        Player playerToSave = playerMapper.mapToEntity(playerDto);
        playerRepository.save(playerToSave);

        log.info("Successfully collected player: {}", playerToSave.getNickname());
    }

}
