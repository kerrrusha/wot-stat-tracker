package com.kerrrusha.wotstattrackerprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.kerrrusha.wotstattrackerdomain.network.OkHttpTemplate;
import com.kerrrusha.wotstattrackerdomain.network.SimpleOkHttpTemplate;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Timestamp;
import java.time.Duration;

@Configuration
@EnableCaching
@PropertySource("classpath:secret.properties")
public class WotStatTrackerProviderConfiguration {

    @Value("${data.update.cache.expiration.minutes}")
    private Integer cacheExpirationMinutes;

    @Bean
    public OkHttpTemplate okHttpTemplate() {
        return new SimpleOkHttpTemplate(new OkHttpClient());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public Cache<String, Timestamp> dataUpdateCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(cacheExpirationMinutes))
                .build();
    }

}
