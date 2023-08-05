package com.kerrrusha.wotstattrackerweb.mapper;

import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Player;
import org.springframework.stereotype.Component;

import static com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto.buildPlayerRequestDto;

@Component
public class PlayerRequestMapper implements DtoMapper<PlayerRequestDto, Player> {

    @Override
    public PlayerRequestDto mapToDto(Player player) {
        return buildPlayerRequestDto(player.getNickname(), player.getRegion());
    }

    public PlayerRequestDto mapFromResponseDto(PlayerResponseDto playerResponseDto) {
        return buildPlayerRequestDto(playerResponseDto.getNickname(), playerResponseDto.getRegion());
    }

}
