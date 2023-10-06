package com.code.comletableFuture.customUtil;

import io.foldright.cffu.CompletableFutureUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class CFFUDemo {

    private static final ExecutorService myBizThreadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> cf42 = CompletableFuture
                .supplyAsync(() -> 21, myBizThreadPool)  // Run in myBizThreadPool
                .thenApply(n -> n * 2);

        final CompletableFuture<Integer> longTaskA = cf42.thenApplyAsync(n -> {
            try {
                sleep(1001);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return n / 2;
        }, myBizThreadPool);
        final CompletableFuture<Integer> longTaskB = cf42.thenApplyAsync(n -> {
            try {
                sleep(1002);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return n / 2;
        }, myBizThreadPool);
        final CompletableFuture<Integer> longTaskC = cf42.thenApplyAsync(n -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return n * 2;
        }, myBizThreadPool);
        final CompletableFuture<Integer> longFailedTask = cf42.thenApplyAsync(unused -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Bang!");
        }, myBizThreadPool);

        final CompletableFuture<Integer> combined = longTaskA.thenCombine(longTaskB, Integer::sum);
        final CompletableFuture<Integer> combinedWithTimeout = CompletableFutureUtils.orTimeout(combined, 1500, TimeUnit.MILLISECONDS);
        System.out.println("combined result: " + combinedWithTimeout.get());
        final CompletableFuture<Integer> anyOfSuccess = CompletableFutureUtils.anyOfSuccessWithType(longTaskC, longFailedTask);
        System.out.println("anyOfSuccess result: " + anyOfSuccess.get());
    }
}
