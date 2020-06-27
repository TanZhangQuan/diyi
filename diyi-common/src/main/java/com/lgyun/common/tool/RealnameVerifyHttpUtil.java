package com.lgyun.common.tool;

import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.enumeration.HttpRequestMethedEnum;
import java.util.HashMap;
import java.util.Map;

/**
 * http发送专用协助类
 *
 * @author chenxi
 */
public class RealnameVerifyHttpUtil {

    /**
     * @param
     * @return HttpGet返回值
     */
    public static String sendGet(String url) {
        String res = "";
        res = HttpUtil.sendHttp(HttpRequestMethedEnum.HttpGet, url, null, null, 1);
        return res;
    }

    /**
     * 发送post请求
     *
     * @param url    发送地址
     * @param token  交易token
     * @param params body入参
     * @return httpPost返回值
     */
    public static String sendPost(String url, String token, String params) {
        Map<String, String> header = new HashMap();
        header.put("X-Tsign-Open-App-Id", RealnameVerifyConstant.appId);
        header.put("X-Tsign-Open-Token", token);
        header.put("Content-Type", "application/json");
        String res = "";
        res = HttpUtil.sendHttp(HttpRequestMethedEnum.HttpPost, url, header, params, 1);
        return res;
    }

    /**
     * 发送get请求(带有header)
     *
     * @param url   发送地址
     * @param token 交易token
     * @return httpGet返回值
     */
    public static String sendGet2(String url, String token) {
        Map<String, String> header = new HashMap();
        header.put("X-Tsign-Open-App-Id", RealnameVerifyConstant.appId);
        header.put("X-Tsign-Open-Token", token);
        header.put("Content-Type", "application/json");
        String res = "";
        res = HttpUtil.sendHttp(HttpRequestMethedEnum.HttpGet, url, header, null, 1);
        return res;
    }

    /**
     * 发送put请求
     *
     * @param url    发送地址
     * @param token  交易token
     * @param params body入参
     * @return httpPut返回值
     */
    public static String sendPut(String url, String token, String params) {
        Map<String, String> header = new HashMap();
        header.put("X-Tsign-Open-App-Id", RealnameVerifyConstant.appId);
        header.put("X-Tsign-Open-Token", token);
        header.put("Content-Type", "application/json");
        String res = "";
        res = HttpUtil.sendHttp(HttpRequestMethedEnum.HttpPut, url, header, params, 1);
        return res;
    }
}
