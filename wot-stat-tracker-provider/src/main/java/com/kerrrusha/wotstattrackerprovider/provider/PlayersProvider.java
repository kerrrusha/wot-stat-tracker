package com.kerrrusha.wotstattrackerprovider.provider;

import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayersDto;

public interface PlayersProvider {

    WargamingPlayersDto findByNickname(String nickname);

}
