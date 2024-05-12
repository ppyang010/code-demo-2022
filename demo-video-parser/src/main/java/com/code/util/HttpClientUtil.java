package com.code.util;

import cn.hutool.json.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: zhaoxu
 * @description:
 */
public class HttpClientUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static PoolingHttpClientConnectionManager cm = null;
    private static RequestConfig requestConfig = null;

    static {


        LayeredConnectionSocketFactory sslsf = null;
        try {

            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {

            LOGGER.error("创建SSL连接失败");
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        //多线程调用注意配置，根据线程数设定
        cm.setMaxTotal(200);
        //多线程调用注意配置，根据线程数设定
        cm.setDefaultMaxPerRoute(300);
        requestConfig = RequestConfig.custom()
                //数据传输过程中数据包之间间隔的最大时间
                .setSocketTimeout(20000)
                //连接建立时间，三次握手完成时间
                .setConnectTimeout(20000)
                //重点参数
                .setExpectContinueEnabled(true)
                .setConnectionRequestTimeout(10000)
                //重点参数，在请求之前校验链接是否有效
                .setStaleConnectionCheckEnabled(true)
                .build();
    }

    public static CloseableHttpClient getHttpClient() {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }

    public static void closeResponse(CloseableHttpResponse closeableHttpResponse) {
        if (null != closeableHttpResponse) {
            try {
                EntityUtils.consume(closeableHttpResponse.getEntity());
                closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * get请求,params可为null,headers可为null
     *
     * @param headers
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url, JSONObject headers, JSONObject params) {

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        // 创建get请求
        HttpGet httpGet = null;
        List<BasicNameValuePair> paramList = new ArrayList<>();
        if (params != null) {

            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {

                String paramName = iterator.next();
                paramList.add(new BasicNameValuePair(paramName, params.get(paramName).toString()));
            }
        }
        if (url.contains("?")) {
            try {
                httpGet = new HttpGet(url + "&" + EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                httpGet = new HttpGet(url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (headers != null) {

            Iterator iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {

                String headerName = iterator.next().toString();
                httpGet.addHeader(headerName, headers.get(headerName).toString());
            }
        } else {

//            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//            if (request.getHeader(ClosedPathConstants.TOKEN) != null) {
//
//                String token = request.getHeader(ClosedPathConstants.TOKEN);
//                httpGet.addHeader(ClosedPathConstants.TOKEN, token);
//            }
        }
        httpGet.setConfig(requestConfig);
//        httpGet.addHeader("Content-Type", "application/json");
//        httpGet.addHeader("lastOperaTime", String.valueOf(System.currentTimeMillis()));
        String response = getResult(httpClient, httpGet);
        return response;
    }

    private static String getResult(CloseableHttpClient httpClient, HttpRequestBase request) {
        String response = "";
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = httpClient.execute(request);
            HttpEntity entity = closeableHttpResponse.getEntity();
            response = EntityUtils.toString(entity);
            closeResponse(closeableHttpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResponse(closeableHttpResponse);
        }
        return response;
    }

    /**
     * post请求,params可为null,headers可为null
     *
     * @param headers
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String post(String url, JSONObject headers, JSONObject params) {

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {

            Iterator iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {

                String headerName = iterator.next().toString();
                httpPost.addHeader(headerName, headers.get(headerName).toString());
            }
        } else {

//            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//            if (request.getHeader(ClosedPathConstants.TOKEN) != null) {
//
//                String token = request.getHeader(ClosedPathConstants.TOKEN);
//                httpPost.addHeader(ClosedPathConstants.TOKEN, token);
//            }
        }
        httpPost.setConfig(requestConfig);
//        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("lastOperaTime", String.valueOf(System.currentTimeMillis()));
        if (params != null) {

            StringEntity stringEntity = new StringEntity(params.toString(), "UTF-8");
            httpPost.setEntity(stringEntity);
        }
        String response = getResult(httpClient, httpPost);
        return response;
    }


    public static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

        public static final String METHOD_NAME = "DELETE";

        @Override
        public String getMethod() {

            return METHOD_NAME;
        }

        public HttpDeleteWithBody(final String uri) {

            super();
            setURI(URI.create(uri));
        }

        public HttpDeleteWithBody(final URI uri) {

            super();
            setURI(uri);
        }

        public HttpDeleteWithBody() {

            super();
        }
    }
}