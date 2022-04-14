package com.code.demowebflux;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.code.webflux.model.People;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ccy
 * @description
 * @time 2022/4/14 10:16 AM
 */
@Slf4j
public class WebClientDemo {

    @Test
    public void testPostRESTClient() throws InterruptedException {
        People people = People.of(4, "赵六", 1);
        WebClient client = WebClient.create(); // 1 使用WebClient.create方法来创建一个新的WebClient对象
        Flux<People> createdUser = client.post() // 2 使用post方法来创建一个 POST 请求
                .uri("http://localhost:9000/hello2/post") // 3 指定 baseUrl
                .accept(MediaType.APPLICATION_JSON) // 4 配置请求 Header：Content-Type: application/json
                .body(Mono.just(people), People.class) // 5 使用body()方法来设置 POST 请求的内容
                .retrieve() // 6 异步地获取 response 信息，返回值为WebClient.ResponseSpec，retrive()可以看做是exchange()方法的 “快捷版”（exchange()的返回值为ClientResponse）
                .bodyToFlux(People.class); // 7 WebClient.ResponseSpec的bodyToFlux方法把响应内容转换成User对象，最终得到的结果是Flux<User>

        createdUser.subscribe((p)-> log.info(JSONUtil.toJsonStr(p))); // 8 打印出来
        TimeUnit.SECONDS.sleep(1); // 9 由于是异步的，我们将测试线程 sleep 1 秒确保拿到 response，也可以用CountDownLatch


    }


    @Test
    public void testGetRESTClient() throws InterruptedException {
        People people = People.of(4, "赵六", 1);
        WebClient client = WebClient.create(); // 1 使用WebClient.create方法来创建一个新的WebClient对象
        Flux<People> createdUser = client.get() // 2 使用post方法来创建一个 Get 请求
                .uri("http://localhost:9000/hello2/get/ccy") // 3 指定 baseUrl
                .accept(MediaType.APPLICATION_JSON) // 4 配置请求 Header：Content-Type: application/json
//                .body(Mono.just(people), People.class) // 5 使用body()方法来设置 POST 请求的内容
                .retrieve() // 6 异步地获取 response 信息，返回值为WebClient.ResponseSpec，retrive()可以看做是exchange()方法的 “快捷版”（exchange()的返回值为ClientResponse）
                .bodyToFlux(People.class); // 7 WebClient.ResponseSpec的bodyToFlux方法把响应内容转换成User对象，最终得到的结果是Flux<User>

        createdUser.subscribe((p)-> log.info(JSONUtil.toJsonStr(p))); // 8 打印出来
        TimeUnit.SECONDS.sleep(1); // 9 由于是异步的，我们将测试线程 sleep 1 秒确保拿到 response，也可以用CountDownLatch


    }


    @Test
    public void testO() throws InterruptedException {

        List<Integer> list1 = Lists.newArrayList();
        list1.add(3000);
        Integer integer = list1.get(0);
        integer = 6000;
        System.out.println(list1.get(0));


    }
}
