package com.kerrrusha.wotstattrackerprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class WotStatTrackerProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WotStatTrackerProviderApplication.class, args);
    }

}
