package com.kerrrusha.wotstattrackerweb.controller.rest;

import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerStatRestController {

    private static final String ERROR_LOADING_LATEST_STAT = "Server error occured while loading latest stat data. " +
            "Try reload page.";

    @Value("${data.update.post.timeout.seconds}")
    private Integer dataUpdateTimeoutSeconds;

    @Value("${data.update.post.check.millis}")
    private Integer checkUpdatedDataEveryMillis;

    @Value("${data.update.cache.expiration.minutes}")
    private Integer cacheExpirationMinutes;

    private final StatService statService;
    private final StatMapper statMapper;

    @GetMapping({"/{nickname}/currentStat"})
    public StatResponseDto getPlayerCurrentStatJson(@PathVariable String nickname) {
        log.info("#getPlayerCurrentStatJson request from: {}", nickname);

        Stat latestStat = statService.findCurrentStatByNickname(nickname);
        if (statService.dataIsUpToDate(latestStat)) {
            return statMapper.mapToDto(latestStat);
        }

        Optional<Stat> playerCurrentStatOptional = awaitLatestStat(nickname);

        StatResponseDto result;
        if (playerCurrentStatOptional.isEmpty()) {
            log.warn("#getPlayerCurrentStatJson failed: {}", nickname);
            result = StatResponseDto.builder().error(ERROR_LOADING_LATEST_STAT).build();
        } else {
            log.info("#getPlayerCurrentStatJson success: {}", nickname);
            result = statMapper.mapToDto(playerCurrentStatOptional.get());
        }

        return result;
    }

    private Optional<Stat> awaitLatestStat(String nickname) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Stat> future = new CompletableFuture<>();

        executor.submit(() -> {
            while (!future.isDone()) {
                Stat stat = statService.findCurrentStatByNickname(nickname);

                if (stat != null) {
                    LocalDateTime acceptedFromTime = LocalDateTime.now().minusMinutes(cacheExpirationMinutes);
                    if (stat.getCreatedAt().isAfter(acceptedFromTime)) {
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
        } catch (Exception ignored) {
        } finally {
            executor.shutdownNow();
        }

        return Optional.ofNullable(resultStat);
    }

}
