package com.kerrrusha.wotstattrackerweb.controller;

import com.kerrrusha.wotstattrackerweb.async.LatestStatAwaitService;
import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.entity.Region;
import com.kerrrusha.wotstattrackerweb.mapper.StatMapper;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto.buildPlayerRequestDto;
import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/{region}/player")
@RequiredArgsConstructor
public class StatRestController {

    private static final String ERROR_LOADING_LATEST_STAT = "Server error occurred while loading latest stat data. " +
            "Try reload page.";
    private static final String ERROR_LOADING_LATEST_STAT_DELTAS = "Server error occurred while loading latest stat data " +
            "for calculating deltas. Try reload page.";

    private final StatService statService;
    private final StatMapper statMapper;
    private final LatestStatAwaitService latestStatAwaitService;

    @GetMapping("/{nickname}/stat-graphs")
    public StatGraphsResponseDto getPlayerStatGraphs(@PathVariable String region, @PathVariable String nickname) {
        PlayerRequestDto playerRequestDto = buildPlayerRequestDto(nickname, Region.parseRegion(region));
        log.info("#getPlayerStatGraphs request from: {}", playerRequestDto);
        return statService.getStatGraphs(playerRequestDto);
    }

    @GetMapping({"/{nickname}/current-stat"})
    public StatResponseDto getPlayerCurrentStat(@PathVariable String region, @PathVariable String nickname) {
        PlayerRequestDto playerRequestDto = buildPlayerRequestDto(nickname, Region.parseRegion(region));
        log.info("#getPlayerCurrentStat request from: {}", playerRequestDto);

        Stat latestStat = statService.findCurrentStatByPlayer(playerRequestDto);
        if (statService.dataIsUpToDate(latestStat)) {
            return statMapper.mapToDto(latestStat);
        }

        Stat playerCurrentStat = latestStatAwaitService.awaitResult(playerRequestDto);

        StatResponseDto result;
        if (isNull(playerCurrentStat)) {
            log.warn("#getPlayerCurrentStat failed: {}", nickname);
            result = StatResponseDto.builder().error(ERROR_LOADING_LATEST_STAT).build();
        } else {
            latestStat = playerCurrentStat;
            log.info("#getPlayerCurrentStat success: {}", nickname);
            result = statMapper.mapToDto(latestStat);
        }

        return result;
    }

    @GetMapping({"/{nickname}/stat-deltas"})
    public ResponseEntity<StatDeltaResponseDto> getPlayerStatDeltas(@PathVariable String region, @PathVariable String nickname) {
        PlayerRequestDto playerRequestDto = buildPlayerRequestDto(nickname, Region.parseRegion(region));
        log.info("#getPlayerStatDeltas request from: {}", playerRequestDto);

        Stat latestStat = statService.findCurrentStatByPlayer(playerRequestDto);
        log.debug(String.valueOf(latestStat));
        if (statService.dataIsUpToDate(latestStat)) {
            Optional<StatDeltaResponseDto> playerStatDeltaOptional = statService.getDeltas(latestStat);
            return playerStatDeltaOptional
                    .map(statDeltaResponseDto -> new ResponseEntity<>(statDeltaResponseDto, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }

        Stat playerCurrentStat = latestStatAwaitService.awaitResult(playerRequestDto);

        ResponseEntity<StatDeltaResponseDto> result;
        if (isNull(playerCurrentStat)) {
            log.warn("#getPlayerStatDeltas failed: {}", playerRequestDto);
            StatDeltaResponseDto responseDto = StatDeltaResponseDto.builder().error(ERROR_LOADING_LATEST_STAT_DELTAS).build();
            result = new ResponseEntity<>(responseDto, HttpStatus.GATEWAY_TIMEOUT);
        } else {
            latestStat = playerCurrentStat;
            log.info("#getPlayerStatDeltas success: {}", playerRequestDto);
            Optional<StatDeltaResponseDto> responseDto = statService.getDeltas(latestStat);
            result = responseDto
                    .map(statDeltaResponseDto -> new ResponseEntity<>(statDeltaResponseDto, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }

        return result;
    }

}
