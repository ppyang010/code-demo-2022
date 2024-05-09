package com.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 下载b站等视频
 */
@Controller
public class VideoDownloadController {

    @GetMapping({"/index", "/"})
    public String index() {
        return "index";
    }


    @PostMapping("/do/video/parse")
    public String doVideoParse(){
        return "";
    }
}
