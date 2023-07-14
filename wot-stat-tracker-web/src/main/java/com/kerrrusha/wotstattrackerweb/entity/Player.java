package com.kerrrusha.wotstattrackerweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "nickname"})})
public class Player extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<Stat> stat = new ArrayList<>();

    @NotBlank
    @Size(min = 3, max = 24)
    @Pattern(regexp = "^[A-Za-z0-9_]+$")
    private String nickname;

    @Column(name = "account_id")
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
