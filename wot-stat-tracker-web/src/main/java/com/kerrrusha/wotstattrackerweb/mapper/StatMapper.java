package com.kerrrusha.wotstattrackerweb.mapper;

import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class StatMapper implements DtoMapper<StatResponseDto, Stat> {

    private static final String STAT_IS_NULL_ERROR = "Stat must not be null.";

    private final PlayerResponseMapper playerResponseMapper;

    @Value("${allowed.data.update.every.hours}")
    private Integer allowedDataUpdateEveryHours;

    @Override
    public StatResponseDto mapToDto(Stat entity) {
        if (isNull(entity)) {
            throw new IllegalArgumentException(STAT_IS_NULL_ERROR);
        }

        StatResponseDto responseDto = new StatResponseDto();

        responseDto.setPlayer(playerResponseMapper.mapToDto(entity.getPlayer()));
        responseDto.setAccountId(entity.getPlayer().getAccountId());
        responseDto.setBattles(entity.getBattles());
        responseDto.setAvgDamage(entity.getAvgDamage());
        responseDto.setAvgExperience(entity.getAvgExperience());
        responseDto.setWN7(entity.getWN7());
        responseDto.setWN8(entity.getWN8());
        responseDto.setGlobalRating(entity.getWgr());
        responseDto.setWtr(entity.getWtr());
        responseDto.setLastBattleTime(entity.getLastBattleTime());
        responseDto.setCreatedAt(entity.getCreatedAt());
        responseDto.setTreesCut(entity.getTreesCut());
        responseDto.setWins(entity.getWins());
        responseDto.setLosses(entity.getLosses());
        responseDto.setDraws(entity.getDraws());
        responseDto.setBattles(entity.getBattles());
        responseDto.setWinrate(entity.getBattles(), entity.getWins());
        responseDto.setNextDataUpdateTime(entity.getCreatedAt().plusHours(allowedDataUpdateEveryHours));

        return responseDto;
    }

}
