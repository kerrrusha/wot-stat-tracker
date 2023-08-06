package com.kerrrusha.wotstattrackerdata.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdata.mapper.PlayerMapper;
import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerdomain.entity.Player;
import com.kerrrusha.wotstattrackerdomain.repository.PlayerRepository;
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
    public void saveCollectedPlayer(String playerJson) {
        log.debug("Received new player: {}", playerJson);

        PlayerResponseDto responseDto = objectMapper.readValue(playerJson, PlayerResponseDto.class);
        Player playerToSave = playerMapper.map(responseDto);
        playerRepository.save(playerToSave);

        log.info("Successfully saved player: {}", responseDto);
    }

}
