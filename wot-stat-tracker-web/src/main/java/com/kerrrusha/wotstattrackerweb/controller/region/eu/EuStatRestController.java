package com.kerrrusha.wotstattrackerweb.controller.region.eu;

import com.kerrrusha.wotstattrackerweb.controller.region.AbstractStatRestController;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.impl.region.eu.EuStatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eu/player")
public class EuStatRestController extends AbstractStatRestController {

    private final EuStatService statService;

    public EuStatRestController(StatMapper statMapper, EuStatService statService) {
        super(statMapper);
        this.statService = statService;
    }

    @Override
    public StatService getStatService() {
        return statService;
    }

}
