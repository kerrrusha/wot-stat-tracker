package com.kerrrusha.wotstattrackerweb.service.mapper;

import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper implements ResponseDtoMapper<PlayerResponseDto, Player> {

    @Override
    public PlayerResponseDto mapToDto(Player entity) {
        PlayerResponseDto responseDto = new PlayerResponseDto();

        responseDto.setId(entity.getId());
        responseDto.setCreatedAt(entity.getCreatedAt());
        responseDto.setNickname(entity.getNickname());
        responseDto.setAccountId(entity.getAccountId());

        return responseDto;
    }

}
