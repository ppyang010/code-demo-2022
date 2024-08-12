package com.example.demo.ai.web;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OpenAiDemoController {


    @Autowired
    private OpenAiChatClient chatClient;
//
//
//
//    @GetMapping("/ai/generate")
//    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        return Map.of("generation", chatClient.call(message));
//    }
}
