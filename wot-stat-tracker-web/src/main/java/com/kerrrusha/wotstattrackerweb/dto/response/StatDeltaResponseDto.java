package com.kerrrusha.wotstattrackerweb.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

import static com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto.formatter;

@Data
public class StatDeltaResponseDto {

    private Integer battlesDelta;
    private Double avgDamageDelta;
    private Integer ratingDelta;
    private String winrateDeltaFormatted;
    private Double avgExperienceDelta;
    private Double wn7Delta;
    private Double wn8Delta;
    private Integer treesCutDelta;

    private LocalDateTime previousStatCreationTime;

    public String getPreviousStatCreationTimeFormatted() {
        return previousStatCreationTime.format(formatter);
    }

    public String getBattlesDeltaFormatted() {
        return formatDelta(battlesDelta);
    }

    public String getAvgDamageDeltaFormatted() {
        return formatDelta(avgDamageDelta);
    }

    public String getRatingDeltaFormatted() {
        return formatDelta(ratingDelta);
    }

    public String getAvgExperienceDeltaFormatted() {
        return formatDelta(avgExperienceDelta);
    }

    public String getWn7DeltaFormatted() {
        return formatDelta(wn7Delta);
    }

    public String getWn8DeltaFormatted() {
        return formatDelta(wn8Delta);
    }

    public String getTreesCutDeltaFormatted() {
        return formatDelta(treesCutDelta);
    }

    private static String formatDelta(Integer delta) {
        if (delta == 0) {
            return "-";
        }
        return delta > 0 ? "+"+delta : "-"+delta;
    }

    private static String formatDelta(Double delta) {
        if (delta == 0) {
            return "-";
        }
        return delta > 0 ? "+"+delta : "-"+delta;
    }

}
