package com.kerrrusha.wotstattrackerprovider.mapper.wargaming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerprovider.mapper.AbstractMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerInfoDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WargamingPlayersMapper extends AbstractMapper<WargamingPlayerInfoDto> {

    public WargamingPlayersMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @SneakyThrows
    public WargamingPlayerInfoDto mapToWargamingDto(String content) {
        WargamingPlayerInfoDto result = new WargamingPlayerInfoDto();
        JsonNode rootNode = objectMapper.readTree(content);

        result.setNickname(rootNode.at("/data/0/nickname").asText());
        result.setAccountId(rootNode.at("/data/0/account_id").asText());
        result.setError(rootNode.at("/error/message").asText());

        return result;
    }

    public PlayerResponseDto mapToResponseDto(PlayerRequestDto requestDto, WargamingPlayerInfoDto infoDto) {
        return PlayerResponseDto
                .builder()
                .createdAt(LocalDateTime.now())
                .accountId(infoDto.getAccountId())
                .nickname(infoDto.getNickname())
                .region(requestDto.getRegion())
                .build();
    }

}
