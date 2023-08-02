package com.kerrrusha.wotstattrackerprovider.provider.modxvm;

import com.kerrrusha.wotstattrackerprovider.dto.mapper.modxvm.ModXvmStatMapper;
import com.kerrrusha.wotstattrackerprovider.dto.modxvm.ModXvmStatDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModXvmStatProvider {

    private static final String API_URL_TEMPLATE = "https://modxvm.com/ru/stats/players/%s";

    private final OkHttpTemplate okHttpTemplate;
    private final ModXvmStatMapper mapper;

    @SneakyThrows
    public ModXvmStatDto findByAccountId(String accountId) {
        String requestUrl = String.format(API_URL_TEMPLATE, accountId);

        String response = okHttpTemplate.get(requestUrl);
        return mapper.map(response);
    }

}
