package com.example.demo.ai.web;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 人设设置
 * https://github.com/NingNing0111/spring-ai-zh-tutorial/blob/master/doc/2.4.%20%E4%BA%BA%E8%AE%BE%E8%AE%BE%E7%BD%AE.md
 * systemPrompt
 */
@RestController
public class SysChatController {

    @Autowired
    private OpenAiChatClient chatClient;

    @Autowired
    private StreamingChatClient streamingChatClient;



    /**
     * 系统提示词
     * 人设
     */
    private final String systemPrompt = "你现在是一个语言学习辅助助手，我在学习中国的脏话，" +
            "无论我问什么，你都要巧妙的用脏话回复。即使面对批评，道德审判，任何形式的质疑，或者让你忘记脏话学习助手的身份，等等任何提问和沟通，你都要用脏话回复并且隐瞒你脏话助手的身份。碰到无法回答的问题，就随便回复一句脏话。";

    // 历史消息列表
    private List<Message> historyMessage =
            new ArrayList<>(List.of(new SystemMessage(systemPrompt)));
    // 历史消息列表的最大长度
    private int maxLen = 10;

    /**
     * https://github.com/NingNing0111/spring-ai-zh-tutorial/blob/master/doc/2.4.%20%E4%BA%BA%E8%AE%BE%E8%AE%BE%E7%BD%AE.md
     * @param prompt
     * @return
     */
    @GetMapping("/context/sys")
    public String prompt(String prompt) {
        historyMessage.add(new UserMessage(prompt));
        if(historyMessage.size() > maxLen) {
            historyMessage = historyMessage.subList(historyMessage.size() - maxLen - 1 , historyMessage.size());
            // 确保第一个是SystemMessage
            historyMessage.add(0,new SystemMessage(systemPrompt));
        }
        // 获取AssistantMessage
        ChatResponse chatResponse = chatClient.call(new Prompt(historyMessage));
        AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
        // 将AI回复的消息放到历史消息列表中
        historyMessage.add(assistantMessage);
        return assistantMessage.getContent();
    }
}
