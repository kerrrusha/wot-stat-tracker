package com.kerrrusha.wotstattrackerweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"", "/", "index", "index.html"})
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }

}
