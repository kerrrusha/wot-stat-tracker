package com.kerrrusha.wotstattrackerprovider.dto.wargaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WargamingPlayerExistsDto {

    private String nickname;
    private boolean exists;

}
