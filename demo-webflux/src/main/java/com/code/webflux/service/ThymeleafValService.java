package com.code.webflux.service;

import org.springframework.stereotype.Service;

/**
 * @author ccy
 * @description
 * @time 2022/5/30 3:05 PM
 */
@Service
public class ThymeleafValService {
    /**
     * Spring beans
     * <p>
     * Thymeleaf可以通过@beanName访问Spring应用上下文中注册的bean，如<div th:text="${@urlService.getApplicationUrl()}">...</div>
     * 在这个例子中，@urlService就是在上下文中注册的Spring Bean的名字:
     *
     * @return
     */
    public String getVal() {
        System.out.println("ThymeleafValService getVau doing");
        return "ThymeleafValService return val";
    }
}
