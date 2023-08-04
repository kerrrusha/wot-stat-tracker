package com.kerrrusha.wotstattrackerprovider.dto.activemq;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayerRequestDto {

    private String error;
    private Long id;
    private LocalDateTime createdAt;

    private String nickname;
    private String accountId;

}
