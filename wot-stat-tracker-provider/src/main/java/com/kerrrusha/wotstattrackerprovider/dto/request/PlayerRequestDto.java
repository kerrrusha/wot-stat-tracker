package com.kerrrusha.wotstattrackerprovider.dto.request;

import com.kerrrusha.wotstattrackerdomain.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequestDto {

    private String accountId;
    private String nickname;
    private Region region;

}
