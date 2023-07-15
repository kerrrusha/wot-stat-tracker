package com.kerrrusha.wotstattrackerweb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private Integer globalRating;

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
                Objects.equals(this.globalRating, other.globalRating) &&
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
