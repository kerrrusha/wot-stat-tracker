package com.kerrrusha.wotstattrackerweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping({"", "/"})
public class IndexController {

    @GetMapping({"index", "index.html"})
    public String index() {
        log.info("#index request");
        return "index";
    }

    @GetMapping("feedback")
    public String feedback() {
        log.info("#feedback request");
        return "feedback";
    }

}
