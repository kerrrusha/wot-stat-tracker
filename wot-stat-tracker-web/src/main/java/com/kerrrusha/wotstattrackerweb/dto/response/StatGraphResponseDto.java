package com.kerrrusha.wotstattrackerweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatGraphResponseDto {

    private Map<String, Double> dateToStatValueMap;

}
