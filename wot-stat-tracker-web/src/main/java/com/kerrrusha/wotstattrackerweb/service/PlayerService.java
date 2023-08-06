package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;

public interface PlayerService {

    PlayerResponseDto getPlayerFromDb(PlayerRequestDto playerRequestDto);

    boolean playerExistsInDb(PlayerRequestDto playerRequestDto);

    boolean playerExistsInGame(PlayerRequestDto playerRequestDto);

}
