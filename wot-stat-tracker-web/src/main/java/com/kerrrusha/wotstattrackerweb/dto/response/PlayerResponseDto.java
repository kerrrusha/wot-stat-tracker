package com.kerrrusha.wotstattrackerweb.dto.response;

import com.kerrrusha.wotstattrackerweb.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseDto {

    private String error;
    private Long id;
    private LocalDateTime createdAt;

    private String nickname;
    private String accountId;
    private Region region;

}
