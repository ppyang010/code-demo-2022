package com.code.webflux.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author ccy
 * @description
 * @time 2022/4/12 1:47 PM
 */
@Getter
@Setter
@Accessors(chain = true)
public class People {
    private Integer id;
    private String name;
    private Integer age;

    public static People of(Integer id ,String name,Integer age) {
        return new People().setId(id).setName(name).setAge(age);
    }

    public static People of(Integer id ,String name) {
        return new People().setId(id).setName(name).setAge(0);
    }
}
