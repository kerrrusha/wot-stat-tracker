package com.kerrrusha.wotstattrackerweb.controller.error;

import com.kerrrusha.wotstattrackerweb.dto.response.ErrorResponseDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PAGE_NOT_EXISTS_ERROR = "Page does not exists.";
    private static final String DEFAULT_ERROR_MESSAGE = "Error occurred. Please, try again.";

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String message = DEFAULT_ERROR_MESSAGE;
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                message = PAGE_NOT_EXISTS_ERROR;
            }
        }

        model.addAttribute("errorResponseDto", new ErrorResponseDto(message));
        return "error";
    }

}
