package com.kerrrusha.wotstattrackerdomain.dto.response;

import com.kerrrusha.wotstattrackerdomain.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseDto {

    private static final String RELATIVE_URL_TEMPLATE = "%s/player/%s";
    private static final String EU_FLAG_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/320px-Flag_of_Europe.svg.png";
    private static final String NA_FLAG_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Flag_of_the_United_States.png/320px-Flag_of_the_United_States.png";

    private String error;
    private LocalDateTime createdAt;

    private String nickname;
    private String accountId;
    private Region region;

    public String getRelativeUrl() {
        return String.format(RELATIVE_URL_TEMPLATE, region, nickname);
    }

    public String getRegionImageUrl() {
        if (region == Region.EU) {
            return EU_FLAG_URL;
        }
        if (region == Region.NA) {
            return NA_FLAG_URL;
        }
        return "";
    }

}
