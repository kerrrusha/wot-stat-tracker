package com.kerrrusha.wotstattrackerprovider.provider.wargaming;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractWargamingProvider {

    @Value("${wargaming.applicationId}")
    protected String applicationId;

}
