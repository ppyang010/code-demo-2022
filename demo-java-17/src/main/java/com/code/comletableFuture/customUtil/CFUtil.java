package com.code.comletableFuture.customUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author ccy
 * @description
 * @time 2023/8/21 11:13
 */
public class CFUtil {


    public static CompletableFuture<Void> allOfFastFail(CompletableFuture<?>... cfs) {
        requireCfsAndEleNonNull(cfs);
        final int size = cfs.length;
        if (size == 0) return CompletableFuture.completedFuture(null);
        if (size == 1) return cfs[0].thenApply(v -> null);

        final CompletableFuture[] successOrBeIncomplete = new CompletableFuture[size];
        // NOTE: fill ONE MORE element of failedOrBeIncomplete LATER
        final CompletableFuture[] failedOrBeIncomplete = new CompletableFuture[size + 1];
        fill(cfs, successOrBeIncomplete, failedOrBeIncomplete);

        // NOTE: fill the ONE MORE element of failedOrBeIncomplete HERE:
        //       a cf that is successful when all given cfs success, otherwise be incomplete
        failedOrBeIncomplete[size] = CompletableFuture.allOf(successOrBeIncomplete);

        return (CompletableFuture) CompletableFuture.anyOf(failedOrBeIncomplete);
    }

    private static void fill(CompletableFuture[] cfs,
                             CompletableFuture[] successOrBeIncomplete,
                             CompletableFuture[] failedOrBeIncomplete) {
        final CompletableFuture incomplete = new CompletableFuture();

        for (int i = 0; i < cfs.length; i++) {
            final CompletableFuture cf = cfs[i];

            successOrBeIncomplete[i] = cf.handle((v, ex) -> ex == null ? cf : incomplete)
                    .thenCompose(Function.identity());

            failedOrBeIncomplete[i] = cf.handle((v, ex) -> ex == null ? incomplete : cf)
                    .thenCompose(Function.identity());
        }
    }

    private static void requireCfsAndEleNonNull(CompletableFuture<?>... cfs) {
        requireNonNull(cfs, "cfs is null");
        for (int i = 0; i < cfs.length; i++) {
            requireNonNull(cfs[i], "cf" + i + " is null");
        }
    }

}
