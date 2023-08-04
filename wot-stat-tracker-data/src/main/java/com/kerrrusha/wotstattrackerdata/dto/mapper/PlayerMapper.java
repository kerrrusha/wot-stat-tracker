package com.kerrrusha.wotstattrackerdata.dto.mapper;

import com.kerrrusha.wotstattrackerdata.dto.PlayerDto;
import com.kerrrusha.wotstattrackerdata.entity.Player;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PlayerMapper extends AbstractMapper<Player, PlayerDto> {

    @Override
    public PlayerDto map(Player object) {
        PlayerDto result = new PlayerDto();

        result.setId(object.getId());
        result.setCreatedAt(object.getCreatedAt());
        result.setNickname(object.getNickname());
        result.setAccountId(object.getAccountId());

        return result;
    }

    public Player mapToEntity(PlayerDto playerDto) {
        return Player.builder()
                .createdAt(LocalDateTime.now())
                .accountId(playerDto.getAccountId())
                .nickname(playerDto.getNickname())
                .build();
    }

}
