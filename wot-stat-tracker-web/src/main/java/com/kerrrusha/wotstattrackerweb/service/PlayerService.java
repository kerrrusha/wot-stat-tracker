package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;

public interface PlayerService {

    PlayerResponseDto getPlayer(PlayerRequestDto playerRequestDto);

    boolean playerExistsInDb(PlayerRequestDto playerRequestDto);

    boolean playerExistsInGame(PlayerRequestDto playerRequestDto);

}
