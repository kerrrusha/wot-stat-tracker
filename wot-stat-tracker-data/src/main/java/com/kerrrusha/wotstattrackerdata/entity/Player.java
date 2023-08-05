package com.kerrrusha.wotstattrackerdata.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(value = EnumType.STRING)
    private Region region;

}
