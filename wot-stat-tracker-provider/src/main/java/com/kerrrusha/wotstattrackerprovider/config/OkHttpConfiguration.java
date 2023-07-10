package com.kerrrusha.wotstattrackerprovider.config;

import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import com.kerrrusha.wotstattrackerprovider.network.SimpleOkHttpTemplate;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class OkHttpConfiguration {

    @Bean
    @Primary
    public OkHttpTemplate okHttpTemplate() {
        return new SimpleOkHttpTemplate(new OkHttpClient());
    }

}
