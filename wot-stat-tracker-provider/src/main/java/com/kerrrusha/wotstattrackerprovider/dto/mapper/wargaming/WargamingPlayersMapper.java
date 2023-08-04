package com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.mapper.AbstractMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerInfoDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class WargamingPlayersMapper extends AbstractMapper<WargamingPlayerInfoDto> {

    public WargamingPlayersMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @SneakyThrows
    public WargamingPlayerInfoDto map(String content) {
        WargamingPlayerInfoDto result = new WargamingPlayerInfoDto();
        JsonNode rootNode = objectMapper.readTree(content);

        result.setNickname(rootNode.at("/data/0/nickname").asText());
        result.setAccountId(rootNode.at("/data/0/account_id").asText());
        result.setError(rootNode.at("/error/message").asText());

        return result;
    }

}
