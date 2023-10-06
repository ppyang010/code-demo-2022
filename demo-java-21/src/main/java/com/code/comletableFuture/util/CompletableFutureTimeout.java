package com.code.comletableFuture.util;

import java.util.concurrent.*;
import java.util.function.Function;

public class CompletableFutureTimeout {
    /**
     * Singleton delay scheduler, used only for starting and * cancelling tasks.
     */
    static final class Delayer {
        static ScheduledFuture<?> delay(Runnable command, long delay,
                                        TimeUnit unit) {
            return delayer.schedule(command, delay, unit);
        }

        static final class DaemonThreadFactory implements ThreadFactory {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("CompletableFutureDelayScheduler");
                return t;
            }
        }

        static final ScheduledThreadPoolExecutor delayer;

        // 注意，这里使用一个线程就可以搞定 因为这个线程并不真的执行请求 而是仅仅抛出一个异常
        static {
            (delayer = new ScheduledThreadPoolExecutor(
                    1, new CompletableFutureTimeout.Delayer.DaemonThreadFactory())).
                    setRemoveOnCancelPolicy(true);
        }
    }

    public static <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
        CompletableFuture<T> result = new CompletableFuture<T>();
        // timeout 时间后 抛出TimeoutException 类似于sentinel / watcher
        CompletableFutureTimeout.Delayer.delayer.schedule(() -> result.completeExceptionally(new TimeoutException()), timeout, unit);
        return result;
    }

    /**
     * 哪个先完成 就apply哪一个结果 这是一个关键的API,exceptionally出现异常后返回默认值
     *
     * @param t
     * @param future
     * @param timeout
     * @param unit
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> completeOnTimeout(T t, CompletableFuture<T> future, long timeout, TimeUnit unit) {
        final CompletableFuture<T> timeoutFuture = timeoutAfter(timeout, unit);
        //future和timeoutFuture 任意一个先完成 都会执行fn
        return future.applyToEither(timeoutFuture, Function.identity()).exceptionally((throwable) -> t);
    }

    /**
     * 哪个先完成 就apply哪一个结果 这是一个关键的API，不设置默认值，超时后抛出异常
     *
     * @param future
     * @param timeout
     * @param unit
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> orTimeout(CompletableFuture<T> future, long timeout, TimeUnit unit) {
        final CompletableFuture<T> timeoutFuture = timeoutAfter(timeout, unit);
        return future.applyToEither(timeoutFuture, Function.identity());
    }
}