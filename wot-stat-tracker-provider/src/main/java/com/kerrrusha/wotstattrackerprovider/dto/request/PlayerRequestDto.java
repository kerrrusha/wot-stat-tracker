package com.kerrrusha.wotstattrackerprovider.dto.request;

import lombok.Data;

@Data
public class PlayerRequestDto {

    private String accountId;
    private String nickname;
    private Region region;

    public static PlayerRequestDto buildPlayerRequestDto(String nickname, Region region) {
        PlayerRequestDto playerRequestDto = new PlayerRequestDto();

        playerRequestDto.setNickname(nickname);
        playerRequestDto.setRegion(region);

        return playerRequestDto;
    }

}
