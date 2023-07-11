package com.kerrrusha.wotstattrackerdata.mapper;

import com.kerrrusha.wotstattrackerdata.entity.Stat;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.dto.wotlife.WotLifePlayerStatDto;

public class StatMapper {

    public Stat map(WargamingPlayerPersonalDataDto wargamingPlayerPersonalDataDto, WotLifePlayerStatDto wotLifePlayerStatDto) {
        Stat result = new Stat();

        result.setBattles(wargamingPlayerPersonalDataDto.getBattles());
        result.setWins(wargamingPlayerPersonalDataDto.getWins());
        result.setLosses(wargamingPlayerPersonalDataDto.getLosses());
        result.setDraws(wargamingPlayerPersonalDataDto.getDraws());
        result.setTreesCut(wargamingPlayerPersonalDataDto.getTreesCut());
        result.setGlobalRating(wargamingPlayerPersonalDataDto.getGlobalRating());
        result.setLastBattleTime(wargamingPlayerPersonalDataDto.getLastBattleTime());

        result.setAvgDamage(wotLifePlayerStatDto.getAvgDamage());
        result.setAvgExperience(wotLifePlayerStatDto.getAvgExperience());
        result.setWN7(wotLifePlayerStatDto.getWN7());
        result.setWN8(wotLifePlayerStatDto.getWN8());

        return result;
    }

}
