package com.kerrrusha.wotstattrackerprovider.dto.mapper.activemq;

import com.kerrrusha.wotstattrackerprovider.dto.activemq.StatResponseDto;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.dto.wotlife.WotLifePlayerStatDto;
import org.springframework.stereotype.Component;

@Component
public class StatMapper {

    public StatResponseDto map(WargamingPlayerPersonalDataDto playerPersonalDataDto, WotLifePlayerStatDto playerStatDto) {
        StatResponseDto result = new StatResponseDto();

        result.setAccountId(playerPersonalDataDto.getAccountId());
        result.setLastBattleTime(playerPersonalDataDto.getLastBattleTime());
        result.setGlobalRating(playerPersonalDataDto.getGlobalRating());
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
