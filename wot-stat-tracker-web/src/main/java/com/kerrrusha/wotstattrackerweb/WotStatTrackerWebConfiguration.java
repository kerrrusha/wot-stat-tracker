package com.kerrrusha.wotstattrackerweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.kerrrusha.wotstattrackerdomain.network.OkHttpTemplate;
import com.kerrrusha.wotstattrackerdomain.network.SimpleOkHttpTemplate;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.kerrrusha.wotstattrackerdomain.*")
@EnableJpaRepositories("com.kerrrusha.wotstattrackerdomain.*")
public class WotStatTrackerWebConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public OkHttpTemplate okHttpTemplate() {
        return new SimpleOkHttpTemplate(new OkHttpClient());
    }

}
