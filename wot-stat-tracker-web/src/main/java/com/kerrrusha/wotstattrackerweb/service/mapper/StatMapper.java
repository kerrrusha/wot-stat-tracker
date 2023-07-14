package com.kerrrusha.wotstattrackerweb.service.mapper;

import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import org.springframework.stereotype.Component;

@Component
public class StatMapper implements ResponseDtoMapper<StatResponseDto, Stat> {

    @Override
    public StatResponseDto mapToDto(Stat entity) {
        StatResponseDto responseDto = new StatResponseDto();

        responseDto.setId(entity.getId());
        responseDto.setAccountId(entity.getPlayer().getAccountId());
        responseDto.setBattles(entity.getBattles());
        responseDto.setAvgDamage(entity.getAvgDamage());
        responseDto.setAvgExperience(entity.getAvgExperience());
        responseDto.setWN7(entity.getWN7());
        responseDto.setWN8(entity.getWN8());
        responseDto.setGlobalRating(entity.getGlobalRating());
        responseDto.setLastBattleTime(entity.getLastBattleTime());
        responseDto.setTreesCut(entity.getTreesCut());
        responseDto.setWins(entity.getWins());
        responseDto.setLosses(entity.getLosses());
        responseDto.setDraws(entity.getDraws());
        responseDto.setBattles(entity.getBattles());

        return responseDto;
    }

}
