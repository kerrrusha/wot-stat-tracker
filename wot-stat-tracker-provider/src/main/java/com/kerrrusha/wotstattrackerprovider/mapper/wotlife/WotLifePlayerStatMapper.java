package com.kerrrusha.wotstattrackerprovider.mapper.wotlife;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.mapper.AbstractMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.wotlife.WotLifePlayerStatDto;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import static com.kerrrusha.wotstattrackerprovider.util.JsoupUtil.text;
import static com.kerrrusha.wotstattrackerprovider.util.MappingUtil.mapToDouble;

@Component
public class WotLifePlayerStatMapper extends AbstractMapper<WotLifePlayerStatDto> {

    private static final String AVG_DAMAGE_SELECTOR = "h3:contains(overall) + table th:contains(Damage dealt) + td";
    private static final String AVG_EXPERIENCE_SELECTOR = "h3:contains(overall) + table th:contains(Experience) + td";
    private static final String WN7_SELECTOR = "h3:contains(overall) + table th:contains(wn7) + td";
    private static final String WN8_SELECTOR = "h3:contains(overall) + table th:contains(wn8) + td";

    public WotLifePlayerStatMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @SneakyThrows
    public WotLifePlayerStatDto map(String content) {
        WotLifePlayerStatDto result = new WotLifePlayerStatDto();
        Document document = Jsoup.parse(content);

        result.setAvgDamage(mapToDouble(text(document.selectFirst(AVG_DAMAGE_SELECTOR))));
        result.setAvgExperience(mapToDouble(text(document.selectFirst(AVG_EXPERIENCE_SELECTOR))));
        result.setWN7(mapToDouble(text(document.selectFirst(WN7_SELECTOR))));
        result.setWN8(mapToDouble(text(document.selectFirst(WN8_SELECTOR))));

        return result;
    }

}
