package com.code.webflux.api;

import cn.hutool.json.JSONUtil;
import com.code.webflux.model.People;
import com.code.webflux.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author ccy
 * @description
 * @time 2022/4/12 1:37 PM
 */
@Component
@Slf4j
public class Hello2Handler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring!"));
    }

    public Mono<ServerResponse> helloGet(ServerRequest request) {
        String name = request.pathVariable("name");
        Optional<String> ageOpt = request.queryParam("age");
        People of = People.of(1, name, ageOpt.map(Integer::valueOf).orElse(-1));
        //方式1
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(of));
        //方式2
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(Mono.justOrEmpty(of), People.class));
    }

    /**
     * 这种好像不大行
     */
//    public Mono<People> helloGet2(@PathVariable("name") String name) {
//        People of = People.of(1, name, 0);
//        return Mono.justOrEmpty(of);
//    }

    /**
     * post
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> helloPost(ServerRequest request) {
        Mono<People> peopleMono = request.bodyToMono(People.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(Result.success()));

    }

    /**
     * 客户端 3s后获取数据
     * @param request
     * @return
     */
    public Mono<ServerResponse> helloLine(ServerRequest request) {
        List<People> peopleList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            peopleList.add(People.of(i, "ccy" + i, i));
        }
        Flux<People> peopleFlux = Flux.fromIterable(peopleList).delayElements(Duration.ofSeconds(1));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(peopleFlux,People.class);

    }

    /**
     * 异步
     * stream+json
     * 客户端 每隔1s后获取数据
     * @param request
     * @return
     */
    public Mono<ServerResponse> helloStreamLine(ServerRequest request) {
        List<People> peopleList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            peopleList.add(People.of(i, "ccy" + i, i));
        }
        Flux<People> peopleFlux = Flux.fromIterable(peopleList).delayElements(Duration.ofSeconds(1));
        //sse
//        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
//                .body(peopleFlux,People.class);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(peopleFlux,People.class);

    }



    public Publisher<Void> printPeople(Mono<People> peopleMono) {
        People block = null;
        try {
            block = peopleMono.toFuture().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        log.info(JSONUtil.toJsonStr(block));
        return null;
    }
}
