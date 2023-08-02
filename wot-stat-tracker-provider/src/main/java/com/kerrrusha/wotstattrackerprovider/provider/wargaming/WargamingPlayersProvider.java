package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming.WargamingPlayersMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayersDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WargamingPlayersProvider extends AbstractWargamingProvider {

    private static final String API_URL_TEMPLATE = "https://api.worldoftanks.eu/wot/account/list/?application_id={APPLICATION_ID}&search={SEARCH}&limit={LIMIT}";

    @Value("${wargaming.players.limit}")
    private String limit;

    private final OkHttpTemplate okHttpTemplate;
    private final WargamingPlayersMapper mapper;

    @SneakyThrows
    public WargamingPlayersDto findByNickname(String nickname) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{SEARCH}", nickname)
                .replace("{LIMIT}", limit);

        String response = okHttpTemplate.get(requestUrl);
        return mapper.map(response);
    }

}
