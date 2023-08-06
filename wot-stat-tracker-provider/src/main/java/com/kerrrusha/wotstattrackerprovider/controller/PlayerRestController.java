package com.kerrrusha.wotstattrackerprovider.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerdomain.entity.Region;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerExistsDto;
import com.kerrrusha.wotstattrackerprovider.provider.wargaming.WargamingPlayerInfoProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/{region}/player")
public class PlayerRestController {

    private final WargamingPlayerInfoProvider wargamingPlayerInfoProvider;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${activemq.queue.collected-player-info}")
    private String queueName;

    @GetMapping("/{nickname}/exists-in-game")
    public boolean getPlayerExistsInGame(@PathVariable String region, @PathVariable String nickname) {
        PlayerRequestDto playerRequestDto = PlayerRequestDto
                .builder()
                .region(Region.parseRegion(region))
                .nickname(nickname)
                .build();
        WargamingPlayerExistsDto responseDto = wargamingPlayerInfoProvider.getPlayerExists(playerRequestDto);
        return responseDto.isExists();
    }

    @GetMapping("/{nickname}/fetch-player")
    public PlayerResponseDto fetchPlayer(@PathVariable String region, @PathVariable String nickname) {
        PlayerRequestDto requestDto = PlayerRequestDto
                .builder()
                .region(Region.parseRegion(region))
                .nickname(nickname)
                .build();
        PlayerResponseDto responseDto = wargamingPlayerInfoProvider.fetchPlayer(requestDto);
        sendCollectedData(responseDto);
        return responseDto;
    }

    @SneakyThrows
    private void sendCollectedData(PlayerResponseDto responseDto) {
        String jsonResult = objectMapper.writeValueAsString(responseDto);
        log.info("Sending to {} collected data: {}", queueName, jsonResult);
        jmsTemplate.convertAndSend(queueName, jsonResult);
    }

}
