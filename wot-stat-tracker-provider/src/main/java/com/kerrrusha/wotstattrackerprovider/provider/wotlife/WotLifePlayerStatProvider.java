package com.kerrrusha.wotstattrackerprovider.provider.wotlife;

import com.kerrrusha.wotstattrackerprovider.dto.mapper.wotlife.WotLifePlayerStatMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wotlife.WotLifePlayerStatDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WotLifePlayerStatProvider {

    private static final String API_URL_TEMPLATE = "https://en.wot-life.com/eu/player/%s/";

    private final OkHttpTemplate okHttpTemplate;
    private final WotLifePlayerStatMapper mapper;

    @SneakyThrows
    public WotLifePlayerStatDto findByNickname(String nickname) {
        String requestUrl = String.format(API_URL_TEMPLATE, nickname);

        String response = okHttpTemplate.get(requestUrl);
        return mapper.map(response);
    }

}
