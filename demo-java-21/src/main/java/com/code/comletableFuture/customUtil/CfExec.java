package com.code.comletableFuture.customUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author ccy
 * @description
 * @time 2023/8/21 11:21
 */
public class CfExec {
    private List<CompletableFuture<Void>> cfList;

    public CfExec() {
        this.cfList = new ArrayList<>();
    }

    public static CfExec create() {
        CfExec cfExec = new CfExec();
        return cfExec;
    }

    public CfExec addCf(CompletableFuture<Void> cf) {
        this.cfList.add(cf);
        return this;
    }
    public void allOfFastFail(){
        CompletableFuture<Void>[] array = cfList.toArray(new CompletableFuture[0]);
        CFUtil.allOfFastFail(array);
    }

    public void allOf(){
        CompletableFuture<Void>[] array = cfList.toArray(new CompletableFuture[0]);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(array);
    }
}
