package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerprovider.mapper.wargaming.WargamingPlayersMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerExistsDto;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerInfoDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WargamingPlayerInfoProvider extends AbstractWargamingProvider {

    private static final String API_URL_TEMPLATE = "https://api.worldoftanks.eu/wot/account/list/?application_id={APPLICATION_ID}&type=exact&search={SEARCH}";

    private final OkHttpTemplate okHttpTemplate;
    private final WargamingPlayersMapper mapper;

    @SneakyThrows
    public WargamingPlayerInfoDto findByNickname(PlayerRequestDto playerRequestDto) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{SEARCH}", playerRequestDto.getNickname());

        String response = okHttpTemplate.get(requestUrl);
        return mapper.map(response);
    }

    @SneakyThrows
    public WargamingPlayerExistsDto getPlayerExists(PlayerRequestDto playerRequestDto) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{SEARCH}", playerRequestDto.getNickname());

        String response = okHttpTemplate.get(requestUrl);
        return WargamingPlayerExistsDto.builder()
                .region(playerRequestDto.getRegion())
                .nickname(playerRequestDto.getNickname())
                .exists(!mapper.map(response).isEmpty())
                .build();
    }

}
