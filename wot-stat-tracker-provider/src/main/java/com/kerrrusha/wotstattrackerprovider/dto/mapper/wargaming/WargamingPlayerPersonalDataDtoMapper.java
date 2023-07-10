package com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.dto.mapper.AbstractMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.substringBetween;

@Component
public class WargamingPlayerPersonalDataDtoMapper extends AbstractMapper<WargamingPlayerPersonalDataDto> {

    public WargamingPlayerPersonalDataDtoMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @SneakyThrows
    public WargamingPlayerPersonalDataDto map(String json) {
        WargamingPlayerPersonalDataDto result = new WargamingPlayerPersonalDataDto();
        JsonNode rootNode = objectMapper.readTree(json);
        String accountId = substringBetween(json, "account_id\":", ",");

        result.setAccountId(accountId);
        result.setGlobalRating(rootNode.at("/data/"+accountId+"/global_rating").asInt());
        result.setLastBattleTime(rootNode.at("/data/"+accountId+"/last_battle_time").asLong());
        result.setTreesCut(rootNode.at("/data/"+accountId+"/statistics/trees_cut").asInt());
        result.setBattles(rootNode.at("/data/"+accountId+"/statistics/all/battles").asInt());
        result.setWins(rootNode.at("/data/"+accountId+"/statistics/all/wins").asInt());
        result.setLosses(rootNode.at("/data/"+accountId+"/statistics/all/losses").asInt());
        result.setDraws(rootNode.at("/data/"+accountId+"/statistics/all/draws").asInt());

        return result;
    }

}
