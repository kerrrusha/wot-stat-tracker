package com.kerrrusha.wotstattrackerweb.controller.region.na;

import com.kerrrusha.wotstattrackerweb.controller.region.AbstractPlayerRestController;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.na.NaPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/na/player")
public class NaPlayerRestController extends AbstractPlayerRestController {

    private final NaPlayerService playerService;

    @Override
    public PlayerService getPlayerService() {
        return playerService;
    }

}
