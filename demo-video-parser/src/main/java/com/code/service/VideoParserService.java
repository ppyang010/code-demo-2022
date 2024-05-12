package com.code.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.code.util.HttpClientUtil;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VideoParserService {

    public void doVideoParse() {

        Map<String, String> headersMap = new HashMap<>();

        // Adding headers to the map
        headersMap.put("accept", "*/*");
        headersMap.put("accept-language", "zh");
        headersMap.put("content-type", "application/json");
        headersMap.put("cookie", "NEXT_LOCALE=zh; _ga=GA1.1.1261622510.1712677775; __gads=ID=ed0198c523a5a828:T=1712754247:RT=1712754247:S=ALNI_Mb082X-UUvDyNj5Ltmx-ENz6JjpYw; __gpi=UID=00000de664b58f83:T=1712754247:RT=1712754247:S=ALNI_MaRDBBc9TR4LW7mO-iStAuf_4HDiA; __eoi=ID=765dbe28d045a7f3:T=1712754247:RT=1712754247:S=AA-AfjZRwDaX6iJces5578ATcApF; _ga_8BFPWBG3X1=GS1.1.1715261920.5.1.1715261953.0.0.0");
        headersMap.put("g-footer", "0babfcaa42d680b00f2f01a4647ba5ff");
        headersMap.put("g-timestamp", "1715262060304");
        headersMap.put("origin", "https://snapany.com");
        headersMap.put("priority", "u=1, i");
        headersMap.put("referer", "https://snapany.com/zh/bilibili?link=https://www.bilibili.com/video/BV1nm421u7me/?vd_source=2d31c0efe43014d77fdedd13666e3af4");
        headersMap.put("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"");
        headersMap.put("sec-ch-ua-mobile", "?0");
        headersMap.put("sec-ch-ua-platform", "\"Windows\"");
        headersMap.put("sec-fetch-dest", "empty");
        headersMap.put("sec-fetch-mode", "cors");
        headersMap.put("sec-fetch-site", "same-origin");
        headersMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");

        String s = HttpClientUtil.get("https://snapany.com/api/extract1", JSONUtil.parseObj(headersMap, false), null);
        System.out.println(s);
    }

//    public static void main(String[] args) {
//        Map<String, String> headersMap = new HashMap<>();
//
//        // Adding headers to the map
//        headersMap.put("accept", "*/*");
//        headersMap.put("accept-language", "zh");
//        headersMap.put("content-type", "application/json");
//        headersMap.put("cookie", "NEXT_LOCALE=zh; _ga=GA1.1.1261622510.1712677775; __gads=ID=ed0198c523a5a828:T=1712754247:RT=1712754247:S=ALNI_Mb082X-UUvDyNj5Ltmx-ENz6JjpYw; __gpi=UID=00000de664b58f83:T=1712754247:RT=1712754247:S=ALNI_MaRDBBc9TR4LW7mO-iStAuf_4HDiA; __eoi=ID=765dbe28d045a7f3:T=1712754247:RT=1712754247:S=AA-AfjZRwDaX6iJces5578ATcApF; _ga_8BFPWBG3X1=GS1.1.1715261920.5.1.1715261953.0.0.0");
//        headersMap.put("g-footer", "0babfcaa42d680b00f2f01a4647ba5ff");
//        headersMap.put("g-timestamp", "1715262060304");
//        headersMap.put("origin", "https://snapany.com");
//        headersMap.put("priority", "u=1, i");
//        headersMap.put("referer", "https://snapany.com/zh/bilibili?link=https://www.bilibili.com/video/BV1nm421u7me/?vd_source=2d31c0efe43014d77fdedd13666e3af4");
//        headersMap.put("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"");
//        headersMap.put("sec-ch-ua-mobile", "?0");
//        headersMap.put("sec-ch-ua-platform", "\"Windows\"");
//        headersMap.put("sec-fetch-dest", "empty");
//        headersMap.put("sec-fetch-mode", "cors");
//        headersMap.put("sec-fetch-site", "same-origin");
//        headersMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
//
//        HashMap<String, String> param = Maps.newHashMap();
//        param.put("link","https://www.bilibili.com/video/BV1nm421u7me");
//
//        String s = HttpClientUtil.post("https://snapany.com/api/extract1",
//                JSONUtil.parseObj(headersMap, false),
//                JSONUtil.parseObj(param, false));
//        System.out.println(s);
//    }
}
