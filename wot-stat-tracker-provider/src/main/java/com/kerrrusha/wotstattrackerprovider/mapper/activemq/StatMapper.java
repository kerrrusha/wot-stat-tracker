package com.kerrrusha.wotstattrackerprovider.mapper.activemq;

import com.kerrrusha.wotstattrackerprovider.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.modxvm.ModXvmStatDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.wotlife.WotLifePlayerStatDto;
import org.springframework.stereotype.Component;

@Component
public class StatMapper {

    public StatResponseDto map(WargamingPlayerPersonalDataDto playerPersonalDataDto, WotLifePlayerStatDto playerStatDto,
                               ModXvmStatDto modXvmStatDto) {
        StatResponseDto result = new StatResponseDto();

        result.setAccountId(playerPersonalDataDto.getAccountId());
        result.setLastBattleTime(playerPersonalDataDto.getLastBattleTime());
        result.setWgr(playerPersonalDataDto.getGlobalRating());
        result.setWtr(modXvmStatDto.getWtr());
        result.setTreesCut(playerPersonalDataDto.getTreesCut());
        result.setWins(playerPersonalDataDto.getWins());
        result.setLosses(playerPersonalDataDto.getLosses());
        result.setDraws(playerPersonalDataDto.getDraws());
        result.setBattles(playerPersonalDataDto.getBattles());

        result.setAvgDamage(playerStatDto.getAvgDamage());
        result.setAvgExperience(playerStatDto.getAvgExperience());
        result.setWN7(playerStatDto.getWN7());
        result.setWN8(playerStatDto.getWN8());

        return result;
    }

}
