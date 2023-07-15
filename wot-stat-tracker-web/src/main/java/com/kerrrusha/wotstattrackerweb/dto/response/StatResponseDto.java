package com.kerrrusha.wotstattrackerweb.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.thymeleaf.util.NumberUtils.formatPercent;

@Data
public class StatResponseDto {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, 'at' HH:mm");

    private Long id;
    private String accountId;

    private Double avgDamage;
    private Double avgExperience;
    private Double WN7;
    private Double WN8;
    private Integer globalRating;

    private LocalDateTime lastBattleTime;
    private LocalDateTime createdAt;
    private LocalDateTime nextDataUpdateTime;

    private Integer treesCut;

    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer battles;

    public String getWinrate() {
        return battles == 0
                ? "-"
                : formatPercent((wins / (double) battles), 1, 2, Locale.getDefault());
    }

    public String getNextDataUpdateTimeFormatted() {
        return nextDataUpdateTime.format(formatter);
    }

    public String getLastBattleTimeFormatted() {
        return getLastBattleTime().format(formatter);
    }

    public String getCreatedTimeFormatted() {
        return getCreatedAt().format(formatter);
    }

}
