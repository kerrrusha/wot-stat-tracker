package com.kerrrusha.wotstattrackerprovider.dto.wargaming;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class WargamingPlayerPersonalDataDto {

    private LocalDateTime lastBattleTime;
    private String accountId;
    private Integer globalRating;
    private Integer treesCut;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer battles;

    public void setLastBattleTime(Long lastBattleTimeSeconds) {
        this.lastBattleTime = LocalDateTime.ofEpochSecond(lastBattleTimeSeconds, 0, ZoneOffset.ofHours(6));
    }

}
