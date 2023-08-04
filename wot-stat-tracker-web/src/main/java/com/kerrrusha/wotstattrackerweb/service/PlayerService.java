package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;

public interface PlayerService {

    PlayerResponseDto getPlayer(String nickname);

    boolean playerExistsInDb(String nickname);

    boolean playerExistsInGame(String nickname);

}
