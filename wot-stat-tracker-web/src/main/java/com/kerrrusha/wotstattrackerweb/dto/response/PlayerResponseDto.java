package com.kerrrusha.wotstattrackerweb.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlayerResponseDto {

    private Long id;
    private LocalDateTime createdAt;

    private List<Long> statIds;
    private String nickname;
    private String accountId;

}
