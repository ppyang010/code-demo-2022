package com.code.comletableFuture;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
public class CompleteOnTimeOutMethodTest {
   public static void main(String args[]) throws InterruptedException {
      int a = 10;
      int b = 15;
      CompletableFuture.supplyAsync(() -> {
         try {
            int time = RandomUtil.randomInt(3,8);
            System.out.println("time=" + time);
            TimeUnit.SECONDS.sleep(time);
         } catch(InterruptedException e) {
            e.printStackTrace();
         }
         return a + b;
      })
      .completeOnTimeout(0, 4, TimeUnit.SECONDS)
      .thenAccept(result -> System.out.println(result));

      System.out.println("模拟执行其他业务");
      TimeUnit.SECONDS.sleep(10);
   }
}