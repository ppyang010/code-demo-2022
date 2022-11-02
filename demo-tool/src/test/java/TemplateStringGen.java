import cn.hutool.core.util.StrUtil;

/**
 * @author ccy
 * @description
 * @time 2022/10/28 17:43
 */
public class TemplateStringGen {

    public static void main(String[] args) {
        String template = "update question_record_{} t1\n" +
                "set t1.is_deleted = 1\n" +
                "where t1.id in (\n" +
                "    select tt.maxid\n" +
                "    from (\n" +
                "             select max(id) maxid\n" +
                "             from question_record_{} t2\n" +
                "             group by t2.username, t2.linked_data, t2.exam_type, t2.collector_type\n" +
                "             having count(*) > 1\n" +
                "         ) as tt\n" +
                ");\n";
        for (int i = 0; i < 32; i++) {
            String format = StrUtil.format(template, i, i);
            System.out.println(format);
        }
    }
}
