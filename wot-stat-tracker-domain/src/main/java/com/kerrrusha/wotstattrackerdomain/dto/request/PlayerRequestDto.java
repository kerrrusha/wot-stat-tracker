package com.kerrrusha.wotstattrackerdomain.dto.request;

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

    private String nickname;
    private String accountId;
    private Region region;

    @Override
    public String toString() {
        return region + "-" + nickname;
    }

    public static PlayerRequestDto buildPlayerRequestDto(String nickname, Region region) {
        PlayerRequestDto playerRequestDto = new PlayerRequestDto();

        playerRequestDto.setNickname(nickname);
        playerRequestDto.setRegion(region);

        return playerRequestDto;
    }

}
