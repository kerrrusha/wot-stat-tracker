package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerprovider.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerprovider.mapper.wargaming.WargamingPlayerPersonalDataMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerdomain.network.OkHttpTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WargamingPlayerPersonalDataProvider extends AbstractWargamingProvider {

    private static final String API_URL_TEMPLATE = "https://api.worldoftanks.{REGION}/wot/account/info/?application_id={APPLICATION_ID}&account_id={ACCOUNT_ID}";

    private final OkHttpTemplate okHttpTemplate;
    private final WargamingPlayerPersonalDataMapper mapper;

    @SneakyThrows
    public WargamingPlayerPersonalDataDto findByPlayer(PlayerRequestDto playerRequestDto) {
        String requestUrl = API_URL_TEMPLATE
                .replace("{REGION}", getRegion(playerRequestDto.getRegion()))
                .replace("{APPLICATION_ID}", applicationId)
                .replace("{ACCOUNT_ID}", playerRequestDto.getAccountId());

        String response = okHttpTemplate.get(requestUrl);
        return mapper.mapToWargamingDto(response);
    }

}
