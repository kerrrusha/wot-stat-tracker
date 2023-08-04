package com.kerrrusha.wotstattrackerweb.controller.region;

import com.kerrrusha.wotstattrackerweb.dto.response.ErrorResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.substringAfter;

@Slf4j
@Validated
@RequiredArgsConstructor
public abstract class AbstractPlayerStatController {

    private static final String PLAYER_ERROR = "Error occurred while loading player info. Please, try again.";
    private static final String PLAYER_NOT_EXISTS_IN_GAME_ERROR = "Such player does not exists in WoT EU server.";

    private final StatMapper statMapper;

    public abstract PlayerService getPlayerService();
    public abstract StatService getStatService();

    @GetMapping("/{nickname}")
    public String getPlayerStat(
            @PathVariable
            @Size(min = 3, max = 24)
            @Pattern(regexp = "^[A-Za-z0-9_]+$")
            String nickname,
            Model model) {
        if (!getPlayerService().playerExistsInDb(nickname) && !getPlayerService().playerExistsInGame(nickname)) {
            model.addAttribute("errorResponseDto", new ErrorResponseDto(PLAYER_NOT_EXISTS_IN_GAME_ERROR));
            return "error";
        }

        PlayerResponseDto playerResponseDto = getPlayerService().getPlayer(nickname);
        if (isNull(playerResponseDto)) {
            model.addAttribute("player", PlayerResponseDto.builder().error(PLAYER_ERROR).build());
            return "player-stat";
        }

        model.addAttribute("player", playerResponseDto);
        if (isNotBlank(playerResponseDto.getError())) {
            return "player-stat";
        }

        getStatService().updateDataIfOutdated(playerResponseDto);

//        List<Stat> playerStatDtos = statService.findMostRecentByNickname(nickname);
//        log.debug("Found {} stats for {}", playerStatDtos.size(), nickname);

        Stat playerCurrentStat = getStatService().findCurrentStatByNickname(nickname);
        if (isNull(playerCurrentStat)) {
            log.debug("Current stat is null for {}", nickname);
            model.addAttribute("playerCurrentStat", new StatResponseDto());
            model.addAttribute("statDeltas", new StatDeltaResponseDto());
            return "player-stat";
        }

        StatResponseDto playerCurrentStatDto = statMapper.mapToDto(playerCurrentStat);
        log.debug("{} current stat: {}", nickname, playerCurrentStatDto);
        model.addAttribute("playerCurrentStat", playerCurrentStatDto);

        Optional<StatDeltaResponseDto> playerStatDeltaOptional = getStatService().getDeltas(playerCurrentStat);
        if (playerStatDeltaOptional.isPresent()) {
            log.debug("Found deltas for {}: {}", nickname, playerStatDeltaOptional.get());
            model.addAttribute("statDeltas", playerStatDeltaOptional.get());
        }

        return "player-stat";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleValidationException(ConstraintViolationException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorResponseDto", new ErrorResponseDto(formatErrorMesssage(e.getMessage())));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    private String formatErrorMesssage(String message) {
        return capitalize(substringAfter(message, ":").trim());
    }

    @PostMapping({"/", ""})
    public String redirectPlayerSearch(@RequestParam("nickname") String nickname) {
        log.debug("Redirecting player search: {}", nickname);
        return "redirect:/player/" + nickname;
    }

}
