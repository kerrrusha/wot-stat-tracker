package com.kerrrusha.wotstattrackerdata.mapper;

import com.kerrrusha.wotstattrackerdomain.dto.StatDto;
import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import org.springframework.stereotype.Component;

@Component
public class StatMapper extends AbstractMapper<StatDto, Stat> {

    @Override
    public Stat map(StatDto object) {
        Stat result = new Stat();

        result.setLastBattleTime(object.getLastBattleTime());
        result.setWgr(object.getWgr());
        result.setWtr(object.getWtr());
        result.setTreesCut(object.getTreesCut());
        result.setWins(object.getWins());
        result.setLosses(object.getLosses());
        result.setDraws(object.getDraws());
        result.setBattles(object.getBattles());

        result.setAvgDamage(object.getAvgDamage());
        result.setAvgExperience(object.getAvgExperience());
        result.setWN7(object.getWN7());
        result.setWN8(object.getWN8());

        return result;
    }

}
