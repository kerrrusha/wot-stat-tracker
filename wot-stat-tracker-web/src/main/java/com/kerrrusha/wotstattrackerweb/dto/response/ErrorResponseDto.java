package com.kerrrusha.wotstattrackerweb.dto.response;

import com.kerrrusha.wotstattrackerweb.service.ErrorHeaderGeneratorService;
import com.kerrrusha.wotstattrackerweb.service.impl.ErrorHeaderGeneratorServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ErrorResponseDto {

    @Getter
    private final String message;

    private final ErrorHeaderGeneratorService errorHeaderGeneratorService = new ErrorHeaderGeneratorServiceImpl();

    public String getRandomErrorHeader() {
        return errorHeaderGeneratorService.getRandomErrorHeader();
    }

}
