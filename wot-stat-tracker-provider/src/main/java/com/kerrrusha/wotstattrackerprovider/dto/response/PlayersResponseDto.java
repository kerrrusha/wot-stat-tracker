package com.kerrrusha.wotstattrackerprovider.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayersResponseDto {

    private String status;
    private Meta meta;
    private List<PlayerData> data;

    @Data
    public static class Meta {
        private Integer count;
    }

    @Data
    public static class PlayerData {
        private String nickname;

        @JsonProperty("account_id")
        private Long accountId;
    }

}
