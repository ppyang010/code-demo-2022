package com.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping({"/index", "/"})
    public String index() {
        return "index";
    }


    @PostMapping("/do/video/parse")
    public String doVideoParse(){
        return "";
    }
}
