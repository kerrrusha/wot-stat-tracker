package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.mapper.wargaming.WargamingPlayerPersonalDataDtoMapper;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.network.OkHttpTemplate;
import com.kerrrusha.wotstattrackerprovider.provider.PlayerPersonalDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WargamingPlayerPersonalDataProvider extends AbstractWargamingProvider  implements PlayerPersonalDataProvider {

    private static final String API_URL_TEMPLATE = "https://api.worldoftanks.eu/wot/account/info/?application_id={APPLICATION_ID}&account_id={ACCOUNT_ID}";

    private final OkHttpTemplate okHttpTemplate;
    private final WargamingPlayerPersonalDataDtoMapper mapper;

    @Override
    @SneakyThrows
    public WargamingPlayerPersonalDataDto findByAccountId(String accountId) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{ACCOUNT_ID}", accountId);

        String response = okHttpTemplate.get(requestUrl);
        return mapper.map(response);
    }

}
