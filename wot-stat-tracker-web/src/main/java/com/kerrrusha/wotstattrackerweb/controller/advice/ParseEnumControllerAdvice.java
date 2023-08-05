package com.kerrrusha.wotstattrackerweb.controller.advice;

import com.kerrrusha.wotstattrackerweb.dto.response.ErrorResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Region;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ParseEnumControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String INVALID_REGION_ERROR = "Such region isn't supported by our server. " +
            "Please, contact developers to create an issue at the 'Feedback' page.";

    @ExceptionHandler(value = Region.InvalidServerNameException.class)
    protected ModelAndView handleConflict() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorResponseDto", new ErrorResponseDto(INVALID_REGION_ERROR));
        return modelAndView;
    }

}
