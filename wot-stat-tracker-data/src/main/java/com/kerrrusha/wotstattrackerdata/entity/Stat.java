package com.kerrrusha.wotstattrackerdata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Stat extends BaseEntity {

    @ManyToOne
    private Player player;

    private Double avgDamage;
    private Double avgExperience;
    private Double WN7;
    private Double WN8;

    @Column(name = "global_rating")
    private Integer wgr;

    private Integer wtr;

    private LocalDateTime lastBattleTime;

    private Integer treesCut;

    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer battles;

}
