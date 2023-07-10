package com.kerrrusha.wotstattrackerprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WotStatTrackerProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WotStatTrackerProviderApplication.class, args);
    }

}
