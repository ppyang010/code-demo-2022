package com.code.webflux.model;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> implements Serializable {
    public Result() {
        this.resMs=System.currentTimeMillis();
        this.resTimeStr = DateUtil.now();
    }

    public static final String SUCCESS_CODE = "success";

    private String message;
    private String code;
    private String resTimeStr;
    private Long resMs;
    private T data;



    public static <T> Result<T> success() {
        return new Result<T>().setCode(SUCCESS_CODE).setMessage("");
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>().setCode(SUCCESS_CODE).setData(data).setMessage("");
    }

    public static <T> Result<T> error(String code, String msg) {
        return new Result<T>().setCode(code).setMessage(msg);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>().setCode("TD0300000000").setMessage(msg);
    }


}