package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.request.Region;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerInfoDto;
import com.kerrrusha.wotstattrackerprovider.dto.request.PlayerRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WargamingPlayerInfoProviderTest {

    @Autowired
    WargamingPlayerInfoProvider provider;

    @Test
    void testMyNickname() {
        String nickname = "He_Cm0Tpu_CTaTucTuKy";
        PlayerRequestDto playerRequestDto = PlayerRequestDto.buildPlayerRequestDto(nickname, Region.EU);

        WargamingPlayerInfoDto responseDto = provider.findByNickname(playerRequestDto);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getAccountId());
        assertEquals(nickname, responseDto.getNickname());
    }

    @Test
    void testInvalidNickname() {
        String nickname = "::::::::::";
        PlayerRequestDto playerRequestDto = PlayerRequestDto.buildPlayerRequestDto(nickname, Region.EU);

        WargamingPlayerInfoDto responseDto = provider.findByNickname(playerRequestDto);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getError());
        assertTrue(isBlank(responseDto.getNickname()));
        assertTrue(isBlank(responseDto.getAccountId()));
    }

}
