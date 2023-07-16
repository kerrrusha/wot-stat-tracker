package com.kerrrusha.wotstattrackerweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.thymeleaf.util.NumberUtils.formatPercent;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatResponseDto {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, 'at' HH:mm");

    private String error;
    private String accountId;

    private Double avgDamage;
    private Double avgExperience;
    private Double WN7;
    private Double WN8;
    private Integer globalRating;

    private String lastBattleTime;
    private String createdAt;
    private String nextDataUpdateTime;

    private Integer treesCut;

    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer battles;
    private String winrate;

    public void setWinrate(Integer battles, Integer wins) {
        this.winrate = battles == 0
                ? "-"
                : formatPercent((wins / (double) battles), 1, 2, Locale.getDefault());
    }

    public void setLastBattleTime(LocalDateTime dateTime) {
        this.lastBattleTime = toDataTimeFormatted(dateTime);
    }

    public void setCreatedAt(LocalDateTime dateTime) {
        this.createdAt = toDataTimeFormatted(dateTime);
    }

    public void setNextDataUpdateTime(LocalDateTime dateTime) {
        this.nextDataUpdateTime = toDataTimeFormatted(dateTime);
    }

    private static String toDataTimeFormatted(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

}
