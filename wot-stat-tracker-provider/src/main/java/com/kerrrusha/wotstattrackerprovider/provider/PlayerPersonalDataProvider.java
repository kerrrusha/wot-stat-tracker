package com.kerrrusha.wotstattrackerprovider.provider;

import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;

public interface PlayerPersonalDataProvider {

    WargamingPlayerPersonalDataDto findByAccountId(String accountId);

}
