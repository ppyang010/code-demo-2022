package com.example.demo.ai.web;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OpenAiDemoController {


    @Autowired
    private OpenAiChatClient chatClient;

    @Autowired
    private StreamingChatClient streamingChatClient;


    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }


    @CrossOrigin(origins = "*")
    // 流式调用 将produces声明为文本事件流
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(String prompt) {
        // 将流中的内容按顺序返回
        return streamingChatClient.stream(prompt).flatMapSequential(Flux::just);
    }


    // 历史消息列表
    static List<Message> historyMessage = new ArrayList<>();
    // 历史消息列表的最大长度
    static int maxLen = 10;

    /**
     * https://www.yuque.com/pgthinker/spring-ai/sqg6gv45dbgvdytu
     * 上下文对话
     *
     * @param prompt
     * @return
     */
    @GetMapping("/context")
    public String context(String prompt) {
        // 用户输入的文本是UserMessage
        historyMessage.add(new UserMessage(prompt));
        // 发给AI前对历史消息对列的长度进行检查
        if (historyMessage.size() > maxLen) {
            historyMessage = historyMessage.subList(historyMessage.size() - maxLen - 1, historyMessage.size());
        }
        // 获取AssistantMessage
        ChatResponse chatResponse = chatClient.call(new Prompt(historyMessage));
        AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
        // 将AI回复的消息放到历史消息列表中
        historyMessage.add(assistantMessage);
        return assistantMessage.getContent();
    }
}
