package com.kerrrusha.wotstattrackerprovider.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import com.kerrrusha.wotstattrackerprovider.network.SimpleOkHttpTemplate;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class ApplicationConfiguration {

    @Bean
    public OkHttpTemplate okHttpTemplate() {
        return new SimpleOkHttpTemplate(new OkHttpClient());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
