package com.kerrrusha.wotstattrackerweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WotStatTrackerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WotStatTrackerWebApplication.class, args);
    }

}
