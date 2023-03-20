import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ccy
 * @description
 * @time 2023/3/20 19:13
 */
public class TestMain {
    public static void main(String[] args) {
        String text = "<p style=\"color: rgb(246, 143, 64);\" >【解题要点】血压90/56mmHg；左侧胸部叩诊浊音?听诊呼吸音减低；</p><p>&nbsp;</p><p>【答案精析】</p><p><span style=\"color:rgb(99,200,164);\">正确选项（D）：</span>综合考虑患者诊断为非进行性血胸，可采用胸腔穿刺及时排出积血。首先解除呼吸困难的症状，患者无明显感染症状，暂无需抗感染治疗，大量补液、紧急输血、开胸手术多用于进行性血胸的治疗。（选D，不选ABCE）</p><p>&nbsp;</p><p>【教材提炼】（第 9 版《外科学》P251）病人为非进行性血胸，胸腔积血量少，可采用胸腔穿刺及时排出积血。</p>";

// 定义英文标点符号和中文标点符号数组
        String[] englishPunctuations = {",", ";", ":", ".", "?", "!", "-", "(", ")", "[", "]", "{", "}"};
        String[] chinesePunctuations = {"，", "；", "：", "。", "？", "！", "－", "（", "）", "【", "】", "｛", "｝"};

// 使用正则表达式匹配文本中的标点符号
//        String regex = "(?![^<>]*>)[" + String.join("", englishPunctuations) + "]";
//        String regex="(?![^<>]*>)[,;:.?!-()[]{}]";

//        String regex="(?![^<>]*>)[,;:.?!-()\\[]\\{}]";
        String regex="(?)";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(text);

// 逐个替换文本中的标点符号
        StringBuilder sb = new StringBuilder();
        int lastEnd = 0;
        while (matcher.find()) {
            // 取出匹配到的标点符号及其位置
            String punctuation = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            // 判断标点符号是否在HTML标签内
            if (text.substring(lastEnd, start).replaceAll("<.*?>", "").length() % 2 == 0) {
                // 不在HTML标签内，替换为中文标点符号
                sb.append(text, lastEnd, start).append(punctuationToChinese(punctuation, englishPunctuations, chinesePunctuations));
                lastEnd = end;
            }
        }
        sb.append(text.substring(lastEnd));

// 输出替换后的文本
        System.out.println(sb.toString());

    }

    private static char[] punctuationToChinese(String punctuation, String[] englishPunctuations, String[] chinesePunctuations) {
        System.out.println(punctuation);
        return new char[0];
    }

}
