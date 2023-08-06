package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerdomain.entity.Region;
import com.kerrrusha.wotstattrackerprovider.dto.request.PlayerRequestDto;
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
        PlayerRequestDto playerRequestDto = PlayerRequestDto.builder()
                .nickname(nickname)
                .region(Region.EU)
                .build();

        PlayerResponseDto responseDto = provider.fetchPlayer(playerRequestDto);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getAccountId());
        assertEquals(nickname, responseDto.getNickname());
    }

    @Test
    void testInvalidNickname() {
        String nickname = "::::::::::";
        PlayerRequestDto playerRequestDto =  PlayerRequestDto.builder()
                .nickname(nickname)
                .region(Region.EU)
                .build();

        PlayerResponseDto responseDto = provider.fetchPlayer(playerRequestDto);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getError());
        assertTrue(isBlank(responseDto.getNickname()));
        assertTrue(isBlank(responseDto.getAccountId()));
    }

}
