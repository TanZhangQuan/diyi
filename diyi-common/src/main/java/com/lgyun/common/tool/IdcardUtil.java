package com.lgyun.common.tool;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.enumeration.IdcardSide;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 识别身份证信息工具类
 */
@Slf4j
public class IdcardUtil {

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     */
    public static String getAuth() throws Exception {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + RealnameVerifyConstant.BAIDUOCRAPIKEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + RealnameVerifyConstant.BAIDUOCRSECRETKEY;

        URL realUrl = new URL(getAccessTokenUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : map.keySet()) {
            log.info(key + "--->" + map.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String result = "";
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }

        log.info("百度获取授权结果:" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("access_token");
    }

    /**
     * 身份证识别
     *
     * @param idcardPic 身份证的存储地址
     * @return
     */
    public static Map<String, String> idcardOCR(String idcardPic, IdcardSide idcardSide) throws Exception {

        //获取授权
        String accessToken = getAuth();
        String result = null;
        try {
            //在线图片转换成base64字符串
            String idcardPicBase64 = Base64Util.imageToBase64ByOnline(idcardPic);
            String imgParam = URLEncoder.encode(idcardPicBase64, "UTF-8");
            String param = "id_card_side=" + idcardSide.getValue() + "&image=" + imgParam;
            result = HttpUtil.post(RealnameVerifyConstant.BAIDUOCRURL, accessToken, param);
            log.info("身份证识别结果:" + result);
        } catch (Exception e) {
            log.error("身份证识别异常：" + e.getMessage());
        }

        Map<String, String> idCardInfo = new HashMap<>();
        if (StringUtils.isNotBlank(result)) {
            Map<String, Map<String, Map<String, Object>>> mapMap = new HashMap<>();
            Map<String, Map<String, Map<String, Object>>> map = JsonUtils.jsonToPojo(result, mapMap.getClass());

            Map<String, Map<String, Object>> words_result = map.get("words_result");
            if (words_result.isEmpty()) {
                return null;
            }

            idCardInfo.put("name", words_result.get("姓名") != null ? words_result.get("姓名").get("words").toString() : "");
            idCardInfo.put("idCard", words_result.get("公民身份号码") != null ? words_result.get("公民身份号码").get("words").toString() : "");
            idCardInfo.put("nation", words_result.get("民族") != null ? words_result.get("民族").get("words").toString() : "");
            idCardInfo.put("sex", words_result.get("性别") != null ? words_result.get("性别").get("words").toString() : "");
            idCardInfo.put("birth", words_result.get("出生") != null ? words_result.get("出生").get("words").toString() : "");
            idCardInfo.put("address", words_result.get("住址") != null ? words_result.get("住址").get("words").toString() : "");
        }

        return idCardInfo;
    }

}
