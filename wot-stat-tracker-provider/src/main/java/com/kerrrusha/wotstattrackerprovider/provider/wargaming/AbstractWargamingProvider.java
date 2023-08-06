package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import com.kerrrusha.wotstattrackerdomain.entity.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractWargamingProvider {

    @Value("${wargaming.applicationId}")
    protected String applicationId;

    protected String getRegion(Region region) {
        if (region.equals(Region.EU)) {
            return "eu";
        }
        return "com";
    }

}
