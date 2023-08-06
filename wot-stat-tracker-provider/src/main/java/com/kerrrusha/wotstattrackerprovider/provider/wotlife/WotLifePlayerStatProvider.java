package com.kerrrusha.wotstattrackerprovider.provider.wotlife;

import com.kerrrusha.wotstattrackerprovider.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerprovider.mapper.wotlife.WotLifePlayerStatMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.wotlife.WotLifePlayerStatDto;
import com.kerrrusha.wotstattrackerdomain.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WotLifePlayerStatProvider {

    private static final String API_URL_TEMPLATE = "https://en.wot-life.com/%s/player/%s/";

    private final OkHttpTemplate okHttpTemplate;
    private final WotLifePlayerStatMapper mapper;

    @SneakyThrows
    public WotLifePlayerStatDto findByPlayer(PlayerRequestDto playerRequestDto) {
        String requestUrl = String.format(API_URL_TEMPLATE, playerRequestDto.getRegion(), playerRequestDto.getNickname());

        String response = okHttpTemplate.get(requestUrl);
        return mapper.mapToWargamingDto(response);
    }

}
