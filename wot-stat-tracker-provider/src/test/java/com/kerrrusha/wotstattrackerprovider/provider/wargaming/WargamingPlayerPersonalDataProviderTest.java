package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerPersonalDataDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WargamingPlayerPersonalDataProviderTest {

    @Autowired
    WargamingPlayerPersonalDataProvider provider;

    @Test
    @Disabled
    void testMyAccountId() {
        String accountId = "601662037";

        WargamingPlayerPersonalDataDto responseDto = provider.findByAccountId(accountId);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getLastBattleTime());
        assertEquals(responseDto.getAccountId(), accountId);
        assertTrue(responseDto.getWins() > 0);
        assertTrue(responseDto.getLosses() > 0);
        assertTrue(responseDto.getDraws() > 0);
        assertTrue(responseDto.getBattles() > 0);
        assertTrue(responseDto.getGlobalRating() > 0);
        assertTrue(responseDto.getTreesCut() > 0);
    }

}