package com.kerrrusha.wotstattrackerweb.controller.region;

import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
public abstract class AbstractPlayerRestController {

    private static final String INVALID_NICKNAME_ERROR = "Nickname is invalid: size must be between 3 and 24, allowed characters are: A-Z a-z 0-9 and _";

    public abstract PlayerService getPlayerService();

    @GetMapping("/{nickname}/is-valid")
    public boolean getNicknameIsValid(
            @PathVariable
            @Size(min = 3, max = 24)
            @Pattern(regexp = "^[A-Za-z0-9_]+$")
            String nickname) {
        return true;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleValidationException() {
        return INVALID_NICKNAME_ERROR;
    }

    @GetMapping("/{nickname}/exists-in-db")
    public boolean getPlayerExistsInDb(@PathVariable String nickname) {
        return getPlayerService().playerExistsInDb(nickname);
    }

    @GetMapping("/{nickname}/exists-in-game")
    public boolean getPlayerExistsInGame(@PathVariable String nickname) {
        return getPlayerService().playerExistsInGame(nickname);
    }

    @GetMapping("/{nickname}/info")
    public PlayerResponseDto getPlayer(@PathVariable String nickname) {
        return getPlayerService().getPlayer(nickname);
    }

}
