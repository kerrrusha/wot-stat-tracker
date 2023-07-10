package com.kerrrusha.wotstattrackerprovider.provider;

import com.kerrrusha.wotstattrackerprovider.dto.wotlife.WotLifePlayerStatDto;

public interface PlayerStatProvider {

    WotLifePlayerStatDto findByNickname(String nickname);

}
