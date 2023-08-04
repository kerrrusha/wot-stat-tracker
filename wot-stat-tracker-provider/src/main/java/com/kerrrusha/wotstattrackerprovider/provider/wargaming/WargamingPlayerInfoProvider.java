package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming.WargamingPlayersMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerExistsDto;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerInfoDto;
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
    public WargamingPlayerInfoDto findByNickname(String nickname) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{SEARCH}", nickname);

        String response = okHttpTemplate.get(requestUrl);
        return mapper.map(response);
    }

    @SneakyThrows
    public WargamingPlayerExistsDto getPlayerExists(String nickname) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{SEARCH}", nickname);

        String response = okHttpTemplate.get(requestUrl);
        return WargamingPlayerExistsDto.builder()
                .nickname(nickname)
                .exists(!mapper.map(response).isEmpty())
                .build();
    }

}
