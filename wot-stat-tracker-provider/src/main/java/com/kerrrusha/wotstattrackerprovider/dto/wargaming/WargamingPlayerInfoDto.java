package com.kerrrusha.wotstattrackerprovider.dto.wargaming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@JsonIgnoreProperties({"empty"})
public class WargamingPlayerInfoDto {

    private String error;
    private String nickname;
    private String accountId;

    public boolean isEmpty() {
        return isBlank(error) && isBlank(nickname) && isBlank(accountId);
    }

}
