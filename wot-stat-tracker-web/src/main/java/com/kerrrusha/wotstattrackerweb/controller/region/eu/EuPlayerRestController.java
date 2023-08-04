package com.kerrrusha.wotstattrackerweb.controller.region.eu;

import com.kerrrusha.wotstattrackerweb.controller.region.AbstractPlayerRestController;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.eu.EuPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eu/player")
public class EuPlayerRestController extends AbstractPlayerRestController {

    private final EuPlayerService playerService;

    @Override
    public PlayerService getPlayerService() {
        return playerService;
    }

}
