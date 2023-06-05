package collector;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorsDemo {

    public static void main(String[] args) {
        List<User> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("dxy_" + i);
            user.setCourse("dxy_" + i);
            user.setExamType(i % 4);
            list.add(user);
        }
        int dupIndex = 1;
        User userDuplication = new User();
        userDuplication.setUsername("dxy_" + dupIndex);
        userDuplication.setCourse("dxy_" +8888);
        userDuplication.setExamType(dupIndex % 4);

        list.add(userDuplication);

//        Map<String, User> collect = list.stream().collect(Collectors.toMap(User::getUsername, Function.identity()));
//        System.out.println(JSONUtil.toJsonStr(collect));
//        HashBasedTable<String, Integer, User> table = HashBasedTable.create();

        HashBasedTable<String, Integer, User> collect1 = list.stream().collect(TableCollectorsImpl.ofTableCollectors(User::getUsername, User::getExamType, Function.identity()));

        for (Table.Cell<String, Integer, User> cell : collect1.cellSet()) {
            System.out.println("-------------------------");
            System.out.println(cell.getRowKey());
            System.out.println(cell.getColumnKey());
            System.out.println(cell.getValue().toString());
            System.out.println("-------------------------");
        }


    }


    @Getter
    @Setter
    @ToString
    static class User {
        private String username;
        private Integer examType;
        private String course;
    }
}
