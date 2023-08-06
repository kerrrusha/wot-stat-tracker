package com.kerrrusha.wotstattrackerweb.dto.response;

import com.kerrrusha.wotstattrackerdomain.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerExistsInGameResponseDto {

    private Region region;
    private String nickname;
    private boolean exists;

}

