package com.kerrrusha.wotstattrackerweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Stat extends BaseEntity {

    @ManyToOne
    @ToString.Exclude
    private Player player;

    private Double avgDamage;
    private Double avgExperience;
    private Double WN7;
    private Double WN8;

    //todo rename it in db from "global_rating" to wgr
    @Column(name = "global_rating")
    private Integer wgr;

    private Integer wtr;

    private LocalDateTime lastBattleTime;

    private Integer treesCut;

    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer battles;

    public Double getWinrate() {
        return battles == 0 ? 0 : wins / (double) battles;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Stat other = (Stat) o;
        return Objects.equals(this.avgDamage, other.avgDamage) &&
                Objects.equals(this.avgExperience, other.avgExperience) &&
                Objects.equals(this.WN7, other.WN7) &&
                Objects.equals(this.WN8, other.WN8) &&
                Objects.equals(this.wgr, other.wgr) &&
                Objects.equals(this.wtr, other.wtr) &&
                Objects.equals(this.lastBattleTime, other.lastBattleTime) &&
                Objects.equals(this.treesCut, other.treesCut) &&
                Objects.equals(this.wins, other.wins) &&
                Objects.equals(this.losses, other.losses) &&
                Objects.equals(this.draws, other.draws) &&
                Objects.equals(this.battles, other.battles);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}
