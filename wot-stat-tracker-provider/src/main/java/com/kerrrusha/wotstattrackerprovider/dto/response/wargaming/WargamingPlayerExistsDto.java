package com.kerrrusha.wotstattrackerprovider.dto.response.wargaming;

import com.kerrrusha.wotstattrackerdomain.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WargamingPlayerExistsDto {

    private Region region;
    private String nickname;
    private boolean exists;

}
