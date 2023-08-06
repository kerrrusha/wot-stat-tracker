package com.kerrrusha.wotstattrackerdata.mapper;

import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerdomain.entity.Player;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PlayerMapper extends AbstractMapper<PlayerResponseDto, Player> {

    @Override
    public Player map(PlayerResponseDto playerDto) {
        return Player.builder()
                .createdAt(LocalDateTime.now())
                .accountId(playerDto.getAccountId())
                .nickname(playerDto.getNickname())
                .region(playerDto.getRegion())
                .build();
    }

}
