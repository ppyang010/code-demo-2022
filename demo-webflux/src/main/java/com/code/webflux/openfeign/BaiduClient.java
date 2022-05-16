package com.code.webflux.openfeign;

import feign.RequestLine;

public interface BaiduClient {
    @RequestLine("GET /")
    String index();
}
