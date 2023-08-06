package com.kerrrusha.wotstattrackerprovider.dto.response.wargaming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kerrrusha.wotstattrackerdomain.entity.Region;
import lombok.Data;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@JsonIgnoreProperties({"empty"})
public class WargamingPlayerInfoDto {

    private String error;
    private String nickname;
    private String accountId;
    private Region region;

    public boolean isEmpty() {
        return isBlank(error)
                && isBlank(nickname)
                && isBlank(accountId)
                && isNull(region);
    }

}
