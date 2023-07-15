package com.kerrrusha.wotstattrackerweb.controller;

import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.PlayerMapper;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerStatController {

    private final PlayerService playerService;
    private final StatService statService;

    private final PlayerMapper playerMapper;
    private final StatMapper statMapper;

    @GetMapping("/{nickname}")
    public String getPlayerStat(@PathVariable String nickname, Model model) {
        boolean playerExists = playerService.playerExists(nickname);
        logRequest(nickname, playerExists);

        PlayerResponseDto playerResponseDto = playerMapper.mapToDto(playerService.findByNickname(nickname));
        List<Stat> playerStatDtos = statService.findByNickname(nickname);
        Stat playerCurrentStat = statService.findCurrentStatByNickname(nickname);
        StatResponseDto playerCurrentStatDto = statMapper.mapToDto(playerCurrentStat);

        Optional<Stat> playerPreviousStatOptional = statService.findPreviousStatByNickname(nickname);
        if (playerPreviousStatOptional.isPresent()) {
            StatDeltaResponseDto statDeltaResponseDto = statService.getDeltas(playerCurrentStat, playerPreviousStatOptional.get());
            model.addAttribute("statDeltas", statDeltaResponseDto);
        }

        model.addAttribute("player", playerResponseDto);
        model.addAttribute("playerCurrentStat", playerCurrentStatDto);
        return "player-stat";
    }

    private void logRequest(String nickname, boolean playerExists) {
        String message = playerExists
            ? "Player exists"
            : "There are no such player";
        log.debug(message + ": {}", nickname);
    }

    @PostMapping({"/", ""})
    public String redirectPlayerSearch(@RequestParam("nickname") String nickname) {
        log.debug("Redirecting player search: {}", nickname);
        return "redirect:/player/" + nickname;
    }

}
