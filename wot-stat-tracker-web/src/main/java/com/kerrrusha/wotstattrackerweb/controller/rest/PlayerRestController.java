package com.kerrrusha.wotstattrackerweb.controller.rest;

import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerdomain.entity.Region;
import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/{region}/player")
public class PlayerRestController {

    private static final String INVALID_NICKNAME_ERROR = "Nickname is invalid: size must be between 3 and 24, allowed characters are: A-Z a-z 0-9 and _";

    private final PlayerService playerService;
    
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
    public boolean getPlayerExistsInDb(@PathVariable String region, @PathVariable String nickname) {
        return playerService.playerExistsInDb(PlayerRequestDto.buildPlayerRequestDto(nickname, Region.parseRegion(region)));
    }

    @GetMapping("/{nickname}/get-from-db")
    public PlayerResponseDto getPlayerFromDb(@PathVariable String region, @PathVariable String nickname) {
        return playerService.getPlayerFromDb(PlayerRequestDto.buildPlayerRequestDto(nickname, Region.parseRegion(region)));
    }

}
