package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.PlayersResponseDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WargamingPlayersProviderTest extends WargamingPlayersProvider {

    public WargamingPlayersProviderTest(@Autowired OkHttpTemplate okHttpTemplate, @Autowired ObjectMapper objectMapper) {
        super(okHttpTemplate, objectMapper);
    }

    @Test
    void testMyNickname() {
        String nickname = "He_Cm0Tpu_CTaTucTuKy";

        PlayersResponseDto responseDto = findByNickname(nickname);

        assertNotNull(responseDto);
        assertEquals("ok", responseDto.getStatus());
        assertEquals(1, responseDto.getData().size());
        assertEquals(nickname, responseDto.getData().get(0).getNickname());
    }

}
