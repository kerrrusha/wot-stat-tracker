package com.kerrrusha.wotstattrackerdata.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlayerDto {

    private Long id;
    private LocalDateTime createdAt;

    private List<Long> statIds;
    private String nickname;
    private String accountId;

}
