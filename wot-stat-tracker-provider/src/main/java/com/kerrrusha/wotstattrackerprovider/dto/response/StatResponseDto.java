package com.kerrrusha.wotstattrackerprovider.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatResponseDto {

    private String accountId;

    private Double avgDamage;
    private Double avgExperience;
    private Double WN7;
    private Double WN8;
    private Integer wgr;
    private Integer wtr;

    private LocalDateTime lastBattleTime;

    private Integer treesCut;

    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer battles;

}