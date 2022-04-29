package com.code.demowebflux;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccy
 * @description
 * @time 2022/4/28 2:03 PM
 */
public class StreamDemo {

    public static void main(String[] args) {
        ArrayList<Integer> intList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            intList.add(i);
        }
        List<String> strList = intList.stream()
                .filter(i -> i > 5)
                .map(i -> "string" + i)
                .collect(Collectors.toList());
        System.out.println(JSONUtil.toJsonStr(strList));

    }
}
