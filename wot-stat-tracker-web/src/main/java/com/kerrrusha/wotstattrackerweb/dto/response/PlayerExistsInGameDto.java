package com.kerrrusha.wotstattrackerweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerExistsInGameDto {

    private String nickname;
    private boolean exists;

}

