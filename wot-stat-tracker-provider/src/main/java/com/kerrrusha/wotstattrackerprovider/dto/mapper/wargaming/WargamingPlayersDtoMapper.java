package com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.mapper.AbstractMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayersDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class WargamingPlayersDtoMapper extends AbstractMapper<WargamingPlayersDto> {

    public WargamingPlayersDtoMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @SneakyThrows
    public WargamingPlayersDto map(String json) {
        WargamingPlayersDto result = new WargamingPlayersDto();
        JsonNode rootNode = objectMapper.readTree(json);

        result.setNickname(rootNode.path("data/nickname").asText());
        result.setAccountId(rootNode.path("data/account_id").asText());

        return result;
    }

}
