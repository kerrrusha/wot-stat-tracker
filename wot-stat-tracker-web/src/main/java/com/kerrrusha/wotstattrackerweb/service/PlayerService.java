package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerweb.entity.Player;

public interface PlayerService {

    boolean playerExists(String nickname);

    Player findByNickname(String nickname);

}
