package collector;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.function.Function;

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
        //测试重复的情况
        int dupIndex = 1;
        User userDuplication = new User();
        userDuplication.setUsername("dxy_" + dupIndex);
        userDuplication.setCourse("dxy_" +8888);
        userDuplication.setExamType(dupIndex % 4);

        list.add(userDuplication);

//        Map<String, User> collect = list.stream().collect(Collectors.toMap(User::getUsername, Function.identity()));
//        System.out.println(JSONUtil.toJsonStr(collect));
//        HashBasedTable<String, Integer, User> table = HashBasedTable.create();

        HashBasedTable<String, Integer, User> collect1 = list.stream().collect(BizCollectors.toTable(User::getUsername, User::getExamType, Function.identity()));

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
