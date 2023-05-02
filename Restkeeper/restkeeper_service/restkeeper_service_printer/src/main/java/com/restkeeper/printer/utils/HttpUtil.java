package com.restkeeper.printer.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param map     请求参数，请求参数应该是 map 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> map) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(map);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

        return response.getBody();
    }

    /**
     * description:将map转换成url参数格式: name1=value1&name2=value2
     *
     * @param map
     * @return
     */
    public static String getUrlParamsFromMap(Map<String, String> map) {
        try {
            if (null != map) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                            .append("&");
                }
                String content = stringBuilder.toString();
                if (content.endsWith("&")) {
                    content = content.substring(0, content.length() - 1);
                }
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("map数据异常！" + e);
        }
        return "";
    }
}
