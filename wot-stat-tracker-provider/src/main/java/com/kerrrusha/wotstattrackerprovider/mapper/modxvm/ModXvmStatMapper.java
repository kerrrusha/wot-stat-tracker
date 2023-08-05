package com.kerrrusha.wotstattrackerprovider.mapper.modxvm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerprovider.mapper.AbstractMapper;
import com.kerrrusha.wotstattrackerprovider.dto.response.modxvm.ModXvmStatDto;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import static com.kerrrusha.wotstattrackerprovider.util.JsoupUtil.text;
import static com.kerrrusha.wotstattrackerprovider.util.MappingUtil.mapToInteger;

@Component
public class ModXvmStatMapper extends AbstractMapper<ModXvmStatDto> {

    private static final String WTR_SELECTOR = ".h5:contains(wtr) + .h2";

    public ModXvmStatMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @SneakyThrows
    public ModXvmStatDto map(String content) {
        ModXvmStatDto result = new ModXvmStatDto();
        Document document = Jsoup.parse(content);

        result.setWtr(mapToInteger(text(document.selectFirst(WTR_SELECTOR))));

        return result;
    }

}
