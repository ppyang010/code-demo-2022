package com.code.webflux.config;

import com.code.webflux.openfeign.BaiduClient;
import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ccy
 * @description
 * @time 2022/5/13 6:11 PM
 */
@Configuration
public class FeignConfig {
    @Bean
    public BaiduClient baiduClient() {
        return Feign.builder()
                .target(BaiduClient.class, "https://www.baidu.com");
    }
}
