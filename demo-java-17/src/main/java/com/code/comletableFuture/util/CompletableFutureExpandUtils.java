package com.code.comletableFuture.util;


import java.util.concurrent.*;
import java.util.function.BiConsumer;


public class CompletableFutureExpandUtils {


    public static <T> CompletableFuture<T> orTimeout(CompletableFuture<T> future, long timeout, TimeUnit unit)  {
        if (null == unit) {
            throw new NullPointerException("时间的给定粒度不能为空");
        }
        if (null == future) {
            throw new NullPointerException("异步任务不能为空");
        }
        if (future.isDone()) {
            return future;
        }

        return future.whenComplete(new Canceller(Delayer.delay(new Timeout(future), timeout, unit)));
    }


    static final  class Timeout<T> implements Runnable {
        final CompletableFuture<T> future;

        Timeout(CompletableFuture<T> future) {
            this.future = future;
        }

        public void run() {
            if (null != future && !future.isDone()) {
                future.completeExceptionally(new TimeoutException());
            }
        }
    }


    static final class Canceller implements BiConsumer {
        final Future future;

        Canceller(Future future) {
            this.future = future;
        }

        public void accept(Object ignore, Object ex) {
            if (null == ex && null != future && !future.isDone()) {
                future.cancel(false);
            }
        }
    }


    static final class Delayer {
        static ScheduledFuture<?> delay(Runnable command, long delay, TimeUnit unit) {
            return delayer.schedule(command, delay, unit);
        }

        static final class DaemonThreadFactory implements ThreadFactory {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("CompletableFutureExpandUtilsDelayScheduler");
                return t;
            }
        }

        static final ScheduledThreadPoolExecutor delayer;

        static {
            delayer = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory());
            delayer.setRemoveOnCancelPolicy(true);
        }
    }
}
