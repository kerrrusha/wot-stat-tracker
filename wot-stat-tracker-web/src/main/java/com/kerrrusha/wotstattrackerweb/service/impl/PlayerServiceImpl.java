package com.kerrrusha.wotstattrackerweb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerweb.async.CollectedPlayerAwaitService;
import com.kerrrusha.wotstattrackerweb.async.ExistInGameResultMessageAwaitService;
import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.mapper.PlayerResponseMapper;
import com.kerrrusha.wotstattrackerweb.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYER_DOES_NOT_EXISTS = "Such player does not exists on WoT EU server. Please, check your input.";

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final PlayerRepository playerRepository;
    private final PlayerResponseMapper playerResponseMapper;

    private final CollectedPlayerAwaitService collectedPlayerAwaitService;
    private final ExistInGameResultMessageAwaitService existInGameResultMessageAwaitService;

    @Value("${activemq.queue.players-to-collect-player-info}")
    private String playersToCollectInfoQueue;

    @Value("${activemq.queue.nicknames-to-check-if-exists}")
    private String playersToCheckIfExistsInGameQueue;

    @Override
    public PlayerResponseDto getPlayer(PlayerRequestDto playerRequestDto) {
        if (playerExistsInDb(playerRequestDto)) {
            return findByNickname(playerRequestDto);
        }
        sendMessage(playersToCollectInfoQueue, playerRequestDto);
        return collectedPlayerAwaitService.awaitResult(playerRequestDto);
    }

    private PlayerResponseDto findByNickname(PlayerRequestDto playerRequestDto) {
        Player player = playerRepository.findByNicknameAndRegion(playerRequestDto.getNickname(), playerRequestDto.getRegion())
                .orElseThrow(() -> new IllegalArgumentException(PLAYER_DOES_NOT_EXISTS));
        return playerResponseMapper.mapToDto(player);
    }

    @Override
    public boolean playerExistsInDb(PlayerRequestDto playerRequestDto) {
        return playerRepository.findByNicknameAndRegion(playerRequestDto.getNickname(), playerRequestDto.getRegion()).isPresent();
    }

    @Override
    public boolean playerExistsInGame(PlayerRequestDto playerRequestDto) {
        sendMessage(playersToCheckIfExistsInGameQueue, playerRequestDto);
        return existInGameResultMessageAwaitService.awaitResult(playerRequestDto);
    }

    @SneakyThrows
    private void sendMessage (String queue, PlayerRequestDto playerRequestDto) {
        String json = objectMapper.writeValueAsString(playerRequestDto);
        log.info("Sending to {} message: {}", queue, playerRequestDto);
        jmsTemplate.convertAndSend(queue, json);
    }

}
