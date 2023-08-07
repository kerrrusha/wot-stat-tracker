package com.kerrrusha.wotstattrackerweb.controller;

import com.kerrrusha.wotstattrackerweb.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping({"", "/"})
public class IndexController {

    private final StatService statService;

    @GetMapping
    public String index(Model model) {
        log.info("#index request");
        model.addAttribute("latestStats", statService.findMostRecent());
        return "index";
    }

    @GetMapping("feedback")
    public String feedback() {
        log.info("#feedback request");
        return "feedback";
    }

}
