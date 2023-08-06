package com.kerrrusha.wotstattrackerweb.service.impl;

import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.entity.Player;
import com.kerrrusha.wotstattrackerdomain.network.OkHttpTemplate;
import com.kerrrusha.wotstattrackerdomain.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.mapper.PlayerResponseMapper;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYER_DOES_NOT_EXISTS = "Such player does not exists on WoT %s server. Please, check your input.";

    private final PlayerRepository playerRepository;
    private final PlayerResponseMapper playerResponseMapper;
    private final OkHttpTemplate okHttpTemplate;

    @Value("${wot-stat-tracker-provider.server.port}")
    private int serverPort;

    @Override
    public PlayerResponseDto getPlayerFromDb(PlayerRequestDto playerRequestDto) {
        return findByNickname(playerRequestDto);
    }

    private PlayerResponseDto findByNickname(PlayerRequestDto playerRequestDto) {
        Player player = playerRepository.findByNicknameAndRegion(playerRequestDto.getNickname(), playerRequestDto.getRegion())
                .orElseThrow(() -> new IllegalArgumentException(String.format(PLAYER_DOES_NOT_EXISTS, playerRequestDto.getRegion())));
        return playerResponseMapper.mapToDto(player);
    }

    @Override
    public boolean playerExistsInDb(PlayerRequestDto playerRequestDto) {
        return playerRepository.findByNicknameAndRegion(playerRequestDto.getNickname(), playerRequestDto.getRegion()).isPresent();
    }

    @Override
    @SneakyThrows
    public boolean playerExistsInGame(PlayerRequestDto playerRequestDto) {
        String requestUrl = "http://localhost:" + serverPort + "/provider/" + playerRequestDto.getRegion()
                + "/player/" + playerRequestDto.getNickname() + "/exists-in-game";
        String response = okHttpTemplate.get(requestUrl);
        return Boolean.getBoolean(response);
    }

}
