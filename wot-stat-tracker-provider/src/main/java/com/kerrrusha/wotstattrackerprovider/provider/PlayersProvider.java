package com.kerrrusha.wotstattrackerprovider.provider;

import com.kerrrusha.wotstattrackerprovider.dto.response.PlayersResponseDto;

public interface PlayersProvider {

    PlayersResponseDto findByNickname(String nickname);

}
