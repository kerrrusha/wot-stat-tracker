package com.kerrrusha.wotstattrackerweb.dto.request;

import com.kerrrusha.wotstattrackerweb.entity.Region;
import lombok.Data;

@Data
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
