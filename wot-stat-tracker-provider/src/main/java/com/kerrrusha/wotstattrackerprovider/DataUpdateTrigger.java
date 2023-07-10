package com.kerrrusha.wotstattrackerprovider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataUpdateTrigger {

    @Scheduled(fixedRateString = "${dataupdate.every.milliseconds}")
    public void logHello() {
        log.info("Hello");
    }

}
