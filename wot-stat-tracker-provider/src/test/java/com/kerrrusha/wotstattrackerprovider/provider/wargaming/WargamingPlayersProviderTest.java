package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayersDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WargamingPlayersProviderTest {

    @Autowired
    WargamingPlayersProvider provider;

    @Test
    @Disabled
    void testMyNickname() {
        String nickname = "He_Cm0Tpu_CTaTucTuKy";

        WargamingPlayersDto responseDto = provider.findByNickname(nickname);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getAccountId());
        assertEquals(nickname, responseDto.getNickname());
    }

}
