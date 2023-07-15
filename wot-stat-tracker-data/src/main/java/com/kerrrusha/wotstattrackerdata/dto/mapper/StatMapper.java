package com.kerrrusha.wotstattrackerdata.dto.mapper;

import com.kerrrusha.wotstattrackerdata.dto.StatDto;
import com.kerrrusha.wotstattrackerdata.entity.Stat;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class StatMapper extends AbstractMapper<StatDto, Stat> {

    @Override
    public Stat map(StatDto object) {
        Stat result = new Stat();

        result.setLastBattleTime(object.getLastBattleTime().atOffset(ZoneOffset.ofHours(6)).toLocalDateTime());
        result.setGlobalRating(object.getGlobalRating());
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
