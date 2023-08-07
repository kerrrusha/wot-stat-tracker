package com.kerrrusha.wotstattrackerweb.dto.response;

import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.util.Objects.isNull;
import static org.thymeleaf.util.NumberUtils.formatPercent;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatResponseDto {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMMM, 'at' HH:mm");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private PlayerResponseDto player;

    private String error;
    private String accountId;

    private Double avgDamage;
    private Double avgExperience;
    private Double WN7;
    private Double WN8;
    private Integer globalRating;
    private Integer wtr;

    private String lastBattleTime;
    private String createdAt;
    private String createdAtTime;
    private String nextDataUpdateTime;

    private Integer treesCut;

    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer battles;
    private String winrate;
    private Double winrateValue;

    public void setWinrate(Integer battles, Integer wins) {
        if (isNull(battles) || isNull(wins)) {
            this.winrate = "-";
            return;
        }

        this.winrate = battles == 0
                ? "-"
                : formatPercent((wins / (double) battles), 1, 2, Locale.getDefault());
        this.winrateValue = battles == 0 ? 0 : (wins / (double) battles);
    }

    public void setLastBattleTime(LocalDateTime dateTime) {
        this.lastBattleTime = toDataTimeFormatted(dateTime);
    }

    public void setCreatedAt(LocalDateTime dateTime) {
        this.createdAt = toDataTimeFormatted(dateTime);
        this.createdAtTime = toTimeFormatted(dateTime);
    }

    public void setNextDataUpdateTime(LocalDateTime dateTime) {
        this.nextDataUpdateTime = toDataTimeFormatted(dateTime);
    }

    private static String toDataTimeFormatted(LocalDateTime dateTime) {
        return isNull(dateTime) ? "-" : dateTime.format(DATE_TIME_FORMATTER);
    }

    private static String toTimeFormatted(LocalDateTime dateTime) {
        return isNull(dateTime) ? "-" : dateTime.format(TIME_FORMATTER);
    }

}
