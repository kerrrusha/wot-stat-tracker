package com.kerrrusha.wotstattrackerprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import com.kerrrusha.wotstattrackerprovider.network.SimpleOkHttpTemplate;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WotStatTrackerProviderConfiguration {

    @Bean
    public OkHttpTemplate okHttpTemplate() {
        return new SimpleOkHttpTemplate(new OkHttpClient());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
