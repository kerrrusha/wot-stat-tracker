package com.kerrrusha.wotstattrackerweb.controller.region.na;

import com.kerrrusha.wotstattrackerweb.controller.region.AbstractStatRestController;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.na.NaStatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/na/player")
public class NaStatRestController extends AbstractStatRestController {

    private final NaStatService statService;

    public NaStatRestController(StatMapper statMapper, NaStatService statService) {
        super(statMapper);
        this.statService = statService;
    }

    @Override
    public StatService getStatService() {
        return statService;
    }

}
