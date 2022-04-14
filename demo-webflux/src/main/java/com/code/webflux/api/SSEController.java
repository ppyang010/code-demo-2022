package com.code.webflux.api;

import com.code.webflux.model.Result;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ccy
 * @description
 * @time 2022/4/13 6:21 PM
 */
@RestController
public class SSEController {


    /**
     * 服务器推送事件（SSE）
     * 服务器推送事件（Server-Sent Events，SSE）允许服务器端不断地推送数据到客户端。
     * 相对于 WebSocket 而言，服务器推送事件只支持服务器端到客户端的单向数据传递。
     * 虽然功能较弱，但优势在于 SSE 在已有的 HTTP 协议上使用简单易懂的文本格式来表示传输的数据。
     * 作为 W3C 的推荐规范，SSE 在浏览器端的支持也比较广泛，除了 IE 之外的其他浏览器都提供了支持。
     * 在 IE 上也可以使用 polyfill 库来提供支持。
     * 在服务器端来说，SSE 是一个不断产生新数据的流，非常适合于用反应式流来表示。
     * 在 WebFlux 中创建 SSE 的服务器端是非常简单的。只需要返回的对象的类型是 Flux，就会被自动按照 SSE 规范要求的格式来发送响应。
     *
     * 下面的SseController是一个使用 SSE 的控制器的示例。
     * https://www.haoyizebo.com/posts/1a7dbc52
     * @return
     */
    @GetMapping("/sse/random")
    public Flux<ServerSentEvent<Integer>> random() {
        return Flux.interval(Duration.ofSeconds(1)) // 1
                .map(seq -> {
                            return ServerSentEvent.<Integer>builder() // 2
                                    .event("random")
                                    .id(seq.toString())
                                    .data(ThreadLocalRandom.current().nextInt())
                                    .build();
                        }
                );
    }


    /**
     * 手动设置contentTYpe为sse规范的type text/event-stream
     *
     * @return
     */
    @GetMapping(value = "/sse/random2",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result> random2() {
        return Flux.interval(Duration.ofSeconds(1)) // 1
                .map(seq -> {
                            return Result.success();
                        }
                );
    }
}
