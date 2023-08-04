package com.kerrrusha.wotstattrackerweb.controller.region.na;

import com.kerrrusha.wotstattrackerweb.controller.region.AbstractPlayerStatController;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.na.NaPlayerService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.na.NaStatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/na/player")
public class NaPlayerStatController extends AbstractPlayerStatController {

    private final NaPlayerService playerService;
    private final NaStatService statService;

    public NaPlayerStatController(StatMapper statMapper, NaPlayerService playerService, NaStatService statService) {
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
