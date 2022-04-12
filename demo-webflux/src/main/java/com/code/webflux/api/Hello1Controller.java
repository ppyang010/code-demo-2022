package com.code.webflux.api;

import com.code.webflux.model.People;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ccy
 * @description
 * @time 2022/4/12 1:37 PM
 * <p>
 * Reactor是一个响应式流，它也有对应的发布者(Publisher )，Reactor的发布者用两个类来表示：
 * Mono(返回0或1个元素)
 * Flux(返回0-n个元素)
 */
@RestController
public class Hello1Controller {

    @GetMapping("/hello1/get")
    public Mono<People> get(String name) {
        People of = People.of(1, name, 0);
        // ...
        return Mono.justOrEmpty(of);
    }

    @GetMapping("/hello1/list")
    public Flux<People> list() {
        People of = People.of(1, "ccy1", 0);
        People of1 = People.of(1, "ccy2", 0);

        return null;
    }

}
