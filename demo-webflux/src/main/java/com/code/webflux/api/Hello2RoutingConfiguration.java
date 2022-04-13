package com.code.webflux.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Hello2RoutingConfiguration {
    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(Hello2Handler hello2Handler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello2/one").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), hello2Handler::hello)
                .andRoute(RequestPredicates.GET("/hello2/get/{name}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),hello2Handler::helloGet)
                .andRoute(RequestPredicates.POST("/hello2/post").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),hello2Handler::helloPost)
                .andRoute(RequestPredicates.GET("/hello2/line").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),hello2Handler::helloLine)
                .andRoute(RequestPredicates.GET("/hello2/stream/line").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),hello2Handler::helloStreamLine);
    }


}