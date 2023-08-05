package com.kerrrusha.wotstattrackerprovider.provider.wotlife;

import com.kerrrusha.wotstattrackerprovider.dto.response.wotlife.WotLifePlayerStatDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WotLifePlayerStatProviderTest {

    @Autowired
    WotLifePlayerStatProvider provider;

    @Test
    void testMyNickname() {
        String nickname = "He_Cm0Tpu_CTaTucTuKy";

        WotLifePlayerStatDto responseDto = provider.findByNickname(nickname);

        assertNotNull(responseDto);
        assertTrue(responseDto.getAvgDamage() > 0);
        assertTrue(responseDto.getAvgExperience() > 0);
        assertTrue(responseDto.getWN7() > 0);
        assertTrue(responseDto.getWN8() > 0);
    }

}