package com.code.webflux.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * @author ccy
 * @description
 * @time 2022/5/30 2:38 PM
 */
@Controller
public class HtmlController {

    @GetMapping("/index")
    public Mono<String> webfulxIndex(String name, Model model) {
        model.addAttribute("modelText", "have a good day");
        return Mono.just("index");
    }
}
