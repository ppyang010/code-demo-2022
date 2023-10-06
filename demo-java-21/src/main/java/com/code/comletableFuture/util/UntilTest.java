package com.code.comletableFuture.util;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class UntilTest {
    public static void main(String[] args) throws InterruptedException {
        utilTimeOutTest();
        utilCompleteOnTimeOutTest();
    }

    private static void utilCompleteOnTimeOutTest() throws InterruptedException {
        int a = 10;
        int b = 15;
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                int time = RandomUtil.randomInt(3, 8);
                System.out.println("time=" + time);
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return a + b;
        });
        CompletableFutureTimeout.completeOnTimeout(-1, future, 4, TimeUnit.SECONDS)
                .thenAccept((result) -> {
                    System.out.println(result);
                });

        System.out.println("模拟执行其他业务");
        TimeUnit.SECONDS.sleep(10);

    }

    private static void utilTimeOutTest() throws InterruptedException {
        int a = 10;
        int b = 15;
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                int time = RandomUtil.randomInt(3, 8);
                System.out.println("time=" + time);
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return a + b;
        });
        CompletableFutureTimeout.orTimeout(future, 4, TimeUnit.SECONDS)
                .whenComplete((result, exception) -> {
                    System.out.println(result);
                    if (exception != null)
                        exception.printStackTrace();
                });

        System.out.println("模拟执行其他业务");
        TimeUnit.SECONDS.sleep(10);
    }
}
