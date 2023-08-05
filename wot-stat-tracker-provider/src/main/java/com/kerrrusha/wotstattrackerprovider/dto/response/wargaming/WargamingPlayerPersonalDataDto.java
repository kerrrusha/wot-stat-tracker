package com.kerrrusha.wotstattrackerprovider.dto.response.wargaming;

import lombok.Data;

import java.time.*;

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
        Instant instant = Instant.ofEpochSecond(lastBattleTimeSeconds);
        ZoneId kyivZone = ZoneId.of("Europe/Kiev");
        ZonedDateTime kyivDateTime = instant.atZone(kyivZone);
        this.lastBattleTime = LocalDateTime.from(kyivDateTime);
    }

}
