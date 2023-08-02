package com.kerrrusha.wotstattrackerweb.dto.response;

import lombok.Data;

@Data
public class StatGraphsResponseDto {

    private StatGraphResponseDto wn8Graph;
    private StatGraphResponseDto wn7Graph;
    private StatGraphResponseDto wgrGraph;
    private StatGraphResponseDto wtrGraph;
    private StatGraphResponseDto winrateGraph;

}
