package com.kerrrusha.wotstattrackerprovider.provider.modxvm;

import com.kerrrusha.wotstattrackerprovider.mapper.modxvm.ModXvmStatMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.modxvm.ModXvmStatDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModXvmStatProvider {

    private static final String API_URL_TEMPLATE = "https://modxvm.com/ru/stats/players/%s";

    private final OkHttpTemplate okHttpTemplate;
    private final ModXvmStatMapper mapper;

    @SneakyThrows
    public ModXvmStatDto findByAccountId(String accountId) {
        String requestUrl = String.format(API_URL_TEMPLATE, accountId);

        try {
            String response = okHttpTemplate.get(requestUrl);
            return mapper.map(response);
        } catch (IOException e) {
            log.error("#findByAccountId - {}", e.getCause().toString());
            return new ModXvmStatDto();
        }
    }

}
