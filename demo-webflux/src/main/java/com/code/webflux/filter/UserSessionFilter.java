package com.code.webflux.filter;

import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
public class UserSessionFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        JSONObject item = new JSONObject();
        item.set("sceneName","场景名称A");
        item.set("sceneCode","3243341233");
        item.set("webUrl","webUrl");
        item.set("experimentName","实验名称A");
        item.set("experimentCode","a");
        ArrayList<Object> list = new ArrayList<>();
        list.add(item);
        JSONObject obj = new JSONObject();
        obj.set("list",list);


        Mono<WebSession> session = exchange.getSession();
        // Thymeleaf 从session获取值
        session.doOnNext(s -> {
            System.out.println("UserSessionFilter");
            s.getAttributes().put("info", "filter add session val");
            s.getAttributes().put("abTestInfo", obj);
        }).subscribe();
        return chain.filter(exchange);
    }
}