package com.kerrrusha.wotstattrackerdata.dto;

import com.kerrrusha.wotstattrackerdata.entity.Region;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayerDto {

    private Long id;
    private String error;
    private LocalDateTime createdAt;

    private String nickname;
    private String accountId;
    private Region region;

}
