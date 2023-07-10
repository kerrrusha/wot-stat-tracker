package com.kerrrusha.wotstattrackerweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataUpdateTrigger {

    @Scheduled(fixedRateString = "${fixed-rate-in-milliseconds}")
    public void logHello() {
        log.info("Hello");
    }

}
