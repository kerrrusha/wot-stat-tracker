package com.kerrrusha.wotstattrackerweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"", "/"})
public class IndexController {

    @GetMapping({"index", "index.html"})
    public String index() {
        return "index";
    }

    @GetMapping("feedback")
    public String feedback() {
        return "feedback";
    }

}
