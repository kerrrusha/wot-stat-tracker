package com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.mapper.AbstractMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayersDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class WargamingPlayersMapper extends AbstractMapper<WargamingPlayersDto> {

    public WargamingPlayersMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @SneakyThrows
    public WargamingPlayersDto map(String content) {
        WargamingPlayersDto result = new WargamingPlayersDto();
        JsonNode rootNode = objectMapper.readTree(content);

        result.setNickname(rootNode.at("/data/nickname").asText());
        result.setAccountId(rootNode.at("/data/account_id").asText());

        return result;
    }

}
