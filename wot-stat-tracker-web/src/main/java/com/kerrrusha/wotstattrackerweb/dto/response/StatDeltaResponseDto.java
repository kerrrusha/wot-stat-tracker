package com.kerrrusha.wotstattrackerweb.dto.response;

import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto.DATE_TIME_FORMATTER;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.thymeleaf.util.NumberUtils.formatPercent;

@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatDeltaResponseDto {

    private String error;
    private Integer battlesDelta;
    private Double avgDamageDelta;
    private Integer ratingDelta;

    private Double winrateDelta;
    private String winrateDeltaFormatted;
    private Double avgExperienceDelta;
    private Double wn7Delta;
    private Double wn8Delta;
    private Integer treesCutDelta;

    private LocalDateTime previousStatCreationTime;

    public static StatDeltaResponseDto createDeltas(Stat current, Stat previous) {
        StatDeltaResponseDto result = new StatDeltaResponseDto();
        log.debug("Creating deltas: \n{}\n{}", current.toString(), previous.toString());

        result.setBattlesDelta(current.getBattles() - previous.getBattles());
        result.setAvgDamageDelta(current.getAvgDamage() - previous.getAvgDamage());
        result.setRatingDelta(current.getWgr() - previous.getWgr());
        result.setWinrateDelta(current.getWinrate() - previous.getWinrate());
        result.setAvgExperienceDelta(current.getAvgExperience() - previous.getAvgExperience());
        result.setWn7Delta(current.getWN7() - previous.getWN7());
        result.setWn8Delta(current.getWN8() - previous.getWN8());
        result.setTreesCutDelta(current.getTreesCut() - previous.getTreesCut());

        result.setPreviousStatCreationTime(previous.getCreatedAt());

        return result;
    }

    public void setWinrateDelta(Double delta) {
        this.winrateDelta = delta;
        String winrateDeltaStr = isNull(delta) || delta == 0
                ? EMPTY
                : formatPercent(delta, 1, 4, Locale.getDefault());
        this.winrateDeltaFormatted = winrateDelta > 0 ? "+"+winrateDeltaStr : winrateDeltaStr;
    }

    public String getPreviousStatCreationTimeFormatted() {
        return nonNull(previousStatCreationTime) ? previousStatCreationTime.format(DATE_TIME_FORMATTER) : EMPTY;
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
        if (isNull(delta) || delta == 0) {
            return EMPTY;
        }
        return delta > 0 ? "+"+delta : ""+delta;
    }

    private static String formatDelta(Double delta) {
        if (isNull(delta) || delta == 0) {
            return EMPTY;
        }
        String deltaStr = String.format("%.2f", delta);
        return delta > 0 ? "+"+deltaStr : deltaStr;
    }

    public static String getDeltaCssClass(Double delta) {
        if (isNull(delta) || delta == 0) {
            return "delta-zero";
        }

        if (delta > 0) {
            return "delta-plus";
        } else if (delta < 0) {
            return "delta-minus";
        }
        return "delta-zero";
    }

}
