package com.kerrrusha.wotstattrackerweb.controller.region.eu;

import com.kerrrusha.wotstattrackerweb.controller.region.AbstractPlayerStatController;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.eu.EuPlayerService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.eu.EuStatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eu/player")
public class EuPlayerStatController extends AbstractPlayerStatController {

    private final EuPlayerService playerService;
    private final EuStatService statService;

    public EuPlayerStatController(StatMapper statMapper, EuPlayerService playerService, EuStatService statService) {
        super(statMapper);
        this.playerService = playerService;
        this.statService = statService;
    }

    @Override
    public PlayerService getPlayerService() {
        return playerService;
    }

    @Override
    public StatService getStatService() {
        return statService;
    }

}
