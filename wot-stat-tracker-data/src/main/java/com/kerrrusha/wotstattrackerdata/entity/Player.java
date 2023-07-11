package com.kerrrusha.wotstattrackerdata.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Entity
@Getter
@Setter
public class Player extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<Stat> stat = new ArrayList<>();

    private String nickname;
    private String accountId;

    public void addStat(Stat stat) {
        if (isNull(stat)) {
            this.stat = new ArrayList<>();
        }
        this.stat.add(stat);
        if (nonNull(stat)) {
            stat.setPlayer(this);
        }
    }

}
