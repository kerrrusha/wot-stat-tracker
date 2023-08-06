package com.kerrrusha.wotstattrackerweb.mapper;

import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.entity.Player;
import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import org.springframework.stereotype.Component;

import static com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto.buildPlayerRequestDto;

@Component
public class PlayerRequestMapper implements DtoMapper<PlayerRequestDto, Player> {

    @Override
    public PlayerRequestDto mapToDto(Player player) {
        return buildPlayerRequestDto(player.getNickname(), player.getRegion());
    }

    public PlayerRequestDto mapFromResponseDto(PlayerResponseDto playerResponseDto) {
        return PlayerRequestDto.builder()
                .nickname(playerResponseDto.getNickname())
                .accountId(playerResponseDto.getAccountId())
                .region(playerResponseDto.getRegion())
                .build();
    }

}
