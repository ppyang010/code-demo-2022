package com.code.webflux.api;

import cn.hutool.http.HttpUtil;
import com.code.webflux.model.People;
import com.code.webflux.openfeign.BaiduClient;
import feign.Feign;
import feign.form.FormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * RESTful API
 *
 * @author ccy
 * @description
 * @time 2022/4/12 1:37 PM
 * <p>
 * Reactor是一个响应式流，它也有对应的发布者(Publisher )，Reactor的发布者用两个类来表示：
 * Mono(返回0或1个元素)
 * Flux(返回0-n个元素)
 */
@RestController
@Slf4j
public class Hello1Controller {
    @Autowired
    private BaiduClient baiduClient;

    @GetMapping("/hello1/get")
    public Mono<People> get(String name) {
        People of = People.of(1, name, 0);
        // ...
//        String resp = HttpUtil.get("https://www.baidu.com");
//        System.out.println(resp.length());
//        String index = baiduClient.index();
//        System.out.println(index.length());
        log.info("/hello1/get start");
        Mono<Integer> defer = Mono.defer(() -> {
            log.info("/hello1/get defer");
            String index = baiduClient.index();
            return Mono.justOrEmpty(index.length());
        });
        Integer block = defer.block();
        log.info("/hello1/get end");
        System.out.println(block);
        return Mono.justOrEmpty(of);
    }

    @GetMapping("/hello1/list")
    public Flux<People> list() {
        People of = People.of(1, "ccy1", 0);
        People of1 = People.of(1, "ccy2", 0);
        List<People> peopleList = new ArrayList<>();
        peopleList.add(of);
        peopleList.add(of1);
        return Flux.fromIterable(peopleList);
    }

}
