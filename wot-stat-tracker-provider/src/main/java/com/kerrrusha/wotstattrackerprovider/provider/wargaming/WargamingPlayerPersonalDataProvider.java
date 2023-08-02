package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming.WargamingPlayerPersonalDataMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WargamingPlayerPersonalDataProvider extends AbstractWargamingProvider {

    private static final String API_URL_TEMPLATE = "https://api.worldoftanks.eu/wot/account/info/?application_id={APPLICATION_ID}&account_id={ACCOUNT_ID}";

    private final OkHttpTemplate okHttpTemplate;
    private final WargamingPlayerPersonalDataMapper mapper;

    @SneakyThrows
    public WargamingPlayerPersonalDataDto findByAccountId(String accountId) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{ACCOUNT_ID}", accountId);

        String response = okHttpTemplate.get(requestUrl);
        return mapper.map(response);
    }

}
