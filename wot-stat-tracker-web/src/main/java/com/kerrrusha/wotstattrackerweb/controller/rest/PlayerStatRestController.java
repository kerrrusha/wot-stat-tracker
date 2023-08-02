package com.kerrrusha.wotstattrackerweb.controller.rest;

import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerStatRestController {

    private static final String ERROR_LOADING_LATEST_STAT = "Server error occurred while loading latest stat data. " +
            "Try reload page.";
    private static final String ERROR_LOADING_LATEST_STAT_DELTAS = "Server error occurred while loading latest stat data " +
            "for calculating deltas. Try reload page.";
    private static final String ERROR_CREATING_STAT_DELTAS = "Server error occurred while calculating stat deltas. " +
            "Try reload page.";

    @Value("${data.update.post.timeout.seconds}")
    private Integer dataUpdateTimeoutSeconds;

    @Value("${data.update.post.check.millis}")
    private Integer checkUpdatedDataEveryMillis;

    @Value("${data.update.cache.expiration.minutes}")
    private Integer cacheExpirationMinutes;

    private final StatService statService;
    private final StatMapper statMapper;

    @GetMapping("/{nickname}/stat-graphs")
    public StatGraphsResponseDto getPlayerStatGraphs(@PathVariable String nickname) {
        log.info("#getPlayerStatGraphs request from: {}", nickname);
        return statService.getStatGraphs(nickname);
    }

    @GetMapping({"/{nickname}/current-stat"})
    public StatResponseDto getPlayerCurrentStat(@PathVariable String nickname) {
        log.info("#getPlayerCurrentStat request from: {}", nickname);

        Stat latestStat = statService.findCurrentStatByNickname(nickname);
        if (statService.dataIsUpToDate(latestStat)) {
            return statMapper.mapToDto(latestStat);
        }

        Optional<Stat> playerCurrentStatOptional = awaitLatestStat(nickname);

        StatResponseDto result;
        if (playerCurrentStatOptional.isEmpty()) {
            log.warn("#getPlayerCurrentStat failed: {}", nickname);
            result = StatResponseDto.builder().error(ERROR_LOADING_LATEST_STAT).build();
        } else {
            latestStat = playerCurrentStatOptional.get();
            log.info("#getPlayerCurrentStat success: {}", nickname);
            result = statMapper.mapToDto(latestStat);
        }

        return result;
    }

    @GetMapping({"/{nickname}/stat-deltas"})
    public ResponseEntity<StatDeltaResponseDto> getPlayerStatDeltas(@PathVariable String nickname) {
        log.info("#getPlayerStatDeltas request from: {}", nickname);

        Stat latestStat = statService.findCurrentStatByNickname(nickname);
        log.debug(latestStat.toString());
        if (statService.dataIsUpToDate(latestStat)) {
            Optional<StatDeltaResponseDto> playerStatDeltaOptional = statService.getDeltas(latestStat);
            return playerStatDeltaOptional
                    .map(statDeltaResponseDto -> new ResponseEntity<>(statDeltaResponseDto, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }

        Optional<Stat> playerCurrentStatOptional = awaitLatestStat(nickname);

        ResponseEntity<StatDeltaResponseDto> result;
        if (playerCurrentStatOptional.isEmpty()) {
            log.warn("#getPlayerStatDeltas failed: {}", nickname);
            StatDeltaResponseDto responseDto = StatDeltaResponseDto.builder().error(ERROR_LOADING_LATEST_STAT_DELTAS).build();
            result = new ResponseEntity<>(responseDto, HttpStatus.GATEWAY_TIMEOUT);
        } else {
            latestStat = playerCurrentStatOptional.get();
            log.debug("#getPlayerStatDeltas success: {}", nickname);
            Optional<StatDeltaResponseDto> responseDto = statService.getDeltas(latestStat);
            result = responseDto
                    .map(statDeltaResponseDto -> new ResponseEntity<>(statDeltaResponseDto, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(
                            StatDeltaResponseDto.builder().error(ERROR_CREATING_STAT_DELTAS).build(),
                            HttpStatus.INTERNAL_SERVER_ERROR
                    ));
        }

        return result;
    }

    private Optional<Stat> awaitLatestStat(String nickname) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Stat> future = new CompletableFuture<>();

        executor.submit(() -> {
            while (!future.isDone()) {
                Stat stat = statService.findCurrentStatByNickname(nickname);
                log.debug(statService.findMostRecentByNickname(nickname).stream().map(Stat::toString).collect(Collectors.joining("\n")));

                if (stat != null) {
                    LocalDateTime acceptedFromTime = LocalDateTime.now().minusMinutes(cacheExpirationMinutes);
                    LocalDateTime lastStatTime = stat.getCreatedAt();
                    log.debug("Accepted from time: {}", acceptedFromTime);
                    log.debug("Last stat time: {}", lastStatTime);
                    if (lastStatTime.isAfter(acceptedFromTime)) {
                        future.complete(stat);
                        break;
                    }
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(checkUpdatedDataEveryMillis);
                } catch (InterruptedException ignored) {}
            }
        });

        Stat resultStat = null;
        try {
            resultStat = future.get(dataUpdateTimeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("#awaitLatestStat - ", e);
        } finally {
            executor.shutdownNow();
        }

        log.debug("#awaitLatestStat - awaited such stat: {}", resultStat);
        return Optional.ofNullable(resultStat);
    }

}
