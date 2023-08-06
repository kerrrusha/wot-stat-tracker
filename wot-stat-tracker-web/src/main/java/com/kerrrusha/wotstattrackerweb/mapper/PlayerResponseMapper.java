package com.kerrrusha.wotstattrackerweb.mapper;

import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerdomain.entity.Player;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class PlayerResponseMapper implements DtoMapper<PlayerResponseDto, Player> {

    @Override
    public PlayerResponseDto mapToDto(Player entity) {
        if (isNull(entity)) {
            return null;
        }

        PlayerResponseDto responseDto = new PlayerResponseDto();

        responseDto.setCreatedAt(entity.getCreatedAt());
        responseDto.setNickname(entity.getNickname());
        responseDto.setAccountId(entity.getAccountId());
        responseDto.setRegion(entity.getRegion());

        return responseDto;
    }

}
