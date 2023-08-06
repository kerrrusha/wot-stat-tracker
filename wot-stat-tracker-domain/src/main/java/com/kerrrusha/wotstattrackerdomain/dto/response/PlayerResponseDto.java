package com.kerrrusha.wotstattrackerdomain.dto.response;

import com.kerrrusha.wotstattrackerdomain.entity.Region;
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
    private LocalDateTime createdAt;

    private String nickname;
    private String accountId;
    private Region region;

}
