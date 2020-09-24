package com.lgyun.common.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.dto.ConfigParams;
import com.lgyun.common.dto.ContextInfo;
import com.lgyun.common.dto.IndivInfo;
import com.lgyun.common.enumeration.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

/**
 * 实名认证工具
 *
 * @author tzq
 * @since 2020/6/27 18:00
 */
@Slf4j
public class RealnameVerifyUtil {

    /***
     *
     * @param str 待计算的消息
     * @return MD5计算后摘要值的Base64编码(ContentMD5)
     * @throws Exception 加密过程中的异常信息
     */
    public static String doContentMD5(String str) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md5.update(str.getBytes("UTF-8"));
        // 查询文件MD5的二进制数组（128位）
        byte[] md5Bytes = md5.digest();
        // 把MD5摘要后的二进制数组md5Bytes使用Base64进行编码（而不是对32位的16进制字符串进行编码）
        String contentMD5 = new String(Base64.encodeBase64(md5Bytes), "UTF-8");
        return contentMD5;
    }

    /***
     * 计算请求签名值
     *
     * @param message 待计算的消息
     * @param secret 密钥
     * @return HmacSHA256计算后摘要值的Base64编码
     * @throws Exception 加密过程中的异常信息
     */
    public static String doSignatureBase64(String message, String secret) throws Exception {
        String algorithm = "HmacSHA256";
        String digestBase64;

        Mac hmacSha256 = Mac.getInstance(algorithm);
        byte[] keyBytes = secret.getBytes("UTF-8");
        byte[] messageBytes = message.getBytes("UTF-8");
        hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm));
        // 使用HmacSHA256对二进制数据消息Bytes计算摘要
        byte[] digestBytes = hmacSha256.doFinal(messageBytes);
        // 把摘要后的结果digestBytes转换成十六进制的字符串
        // String digestBase64 = Hex.encodeHexString(digestBytes);
        // 把摘要后的结果digestBytes使用Base64进行编码
        digestBase64 = new String(Base64.encodeBase64(digestBytes), "UTF-8");

        return digestBase64;
    }

    /***
     * 查询时间戳(毫秒级)
     *
     * @return 毫秒级时间戳, 如 1578446909000
     */
    public static long timeStamp() {
        long timeStamp = System.currentTimeMillis();
        return timeStamp;
    }

    /**
     * 身份证实名认证
     */
    public static JSONObject idCardOCR(String infoImg) throws Exception {

        //在线图片转换成base64字符串
        String idcardPicBase64 = Base64Util.imageToBase64ByOnline(infoImg);

        //构建请求Body体
        JSONObject reqBodyObj = new JSONObject();
        reqBodyObj.put("infoImg", idcardPicBase64);

        //发送POST请求
        JSONObject resultJson = realnameVerifyOCR(RealnameVerifyConstant.IDCARDOCRURL, reqBodyObj, HttpMethod.POST);
        return resultJson;
    }

    /**
     * 查询认证信息
     */
    public static JSONObject detail(String flowId) throws Exception {

        //构建URL
        String detailUrl = "/v2/identity/auth/api/common/" + flowId + "/detail";

        //发送POST请求
        return realnameVerifyOCR(detailUrl, new JSONObject(), HttpMethod.GET);
    }

    /**
     * 活体认证
     */
    public static R<JSONObject> faceOCR(Long contextId, String name, String certNo) throws Exception {

        //指定页面显示认证方式
        List<String> availableAuthTypes = new ArrayList<>();
        availableAuthTypes.add("PSN_FACEAUTH_BYURL");

        //业务方交互上下文信息
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setContextId(String.valueOf(contextId));
        contextInfo.setNotifyUrl(RealnameVerifyConstant.FACEOCRNOTIFYURL);

        //个人基本信息
        IndivInfo indivInfo = new IndivInfo();
        indivInfo.setName(name);
        indivInfo.setCertNo(certNo);

        //认证配置信息
        String[] indivUneditableInfo = new String[]{"name", "certNo"};
        ConfigParams configParams = new ConfigParams();
        configParams.setIndivUneditableInfo(indivUneditableInfo);

        //活体认证请求
        return faceBankCardMobileOCR("PSN_FACEAUTH_BYURL", availableAuthTypes, contextInfo, indivInfo, configParams);

    }

    /**
     * 银行卡实名认证
     */
    public static R<JSONObject> bankCardOCR(Long contextId, String name, String certNo, String bankCardNo, String mobileNo) throws Exception {

        //指定页面显示认证方式
        List<String> availableAuthTypes = new ArrayList<>();
        availableAuthTypes.add("PSN_BANK4_AUTHCODE");

        //业务方交互上下文信息
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setContextId(String.valueOf(contextId));
        contextInfo.setNotifyUrl(RealnameVerifyConstant.BANKCARDOCRNOTIFYURL);

        //个人基本信息
        IndivInfo indivInfo = new IndivInfo();
        indivInfo.setName(name);
        indivInfo.setCertNo(certNo);
        indivInfo.setBankCardNo(bankCardNo);
        indivInfo.setMobileNo(mobileNo);

        //认证配置信息
        String[] indivUneditableInfo = new String[]{"name", "certNo", "mobileNo", "bankCardNo"};
        ConfigParams configParams = new ConfigParams();
        configParams.setIndivUneditableInfo(indivUneditableInfo);

        //银行卡实名认证请求
        return faceBankCardMobileOCR("PSN_BANK4_AUTHCODE", availableAuthTypes, contextInfo, indivInfo, configParams);
    }

    /**
     * 手机号实名认证
     */
    public static R<JSONObject> mobileOCR(Long contextId, String name, String certNo, String mobileNo) throws Exception {

        //指定页面显示认证方式
        List<String> availableAuthTypes = new ArrayList<>();
        availableAuthTypes.add("PSN_TELECOM_AUTHCODE");

        //业务方交互上下文信息
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setContextId(String.valueOf(contextId));
        contextInfo.setNotifyUrl(RealnameVerifyConstant.MOBILEOCRNOTIFYURL);

        //个人基本信息
        IndivInfo indivInfo = new IndivInfo();
        indivInfo.setName(name);
        indivInfo.setCertNo(certNo);
        indivInfo.setMobileNo(mobileNo);

        //认证配置信息
        String[] indivUneditableInfo = new String[]{"name", "certNo", "mobileNo"};
        ConfigParams configParams = new ConfigParams();
        configParams.setIndivUneditableInfo(indivUneditableInfo);

        //手机号实名认证请求
        return faceBankCardMobileOCR("PSN_TELECOM_AUTHCODE", availableAuthTypes, contextInfo, indivInfo, configParams);
    }

    /**
     * 活体认证、银行卡认证以及手机认证
     */
    public static R<JSONObject> faceBankCardMobileOCR(String authType, List<String> availableAuthTypes, ContextInfo contextInfo, IndivInfo indivInfo, ConfigParams configParams) throws Exception {

        //构建请求Body体
        JSONObject reqBodyObj = new JSONObject();
        reqBodyObj.put("authType", authType);
        reqBodyObj.put("availableAuthTypes", availableAuthTypes);
        reqBodyObj.put("contextInfo", contextInfo);
        reqBodyObj.put("indivInfo", indivInfo);
        reqBodyObj.put("configParams", configParams);

        //发送POST请求
        JSONObject resultJson = realnameVerifyOCR(RealnameVerifyConstant.FACEBANKCARDMOBILEOCROCRURL, reqBodyObj, HttpMethod.POST);
        if (resultJson == null) {
            return R.fail("实名认证失败");
        }

        return R.data(resultJson);
    }

    /**
     * 实名认证签名请求封装
     */
    public static JSONObject realnameVerifyOCR(String OcrApi, JSONObject reqBodyObj, HttpMethod httpMethod) throws Exception {
        //接口请求地址
        String OcrUrl = RealnameVerifyConstant.HOST + OcrApi;

        //请求Body体数据
        String reqBodyData = reqBodyObj.toString();
        //对请求Body体内的数据计算ContentMD5
        String contentMD5 = doContentMD5(reqBodyData);

        // 构建待签名字符串
        String method = "POST";
        String accept = "*/*";
        String contentType = "application/json; charset=UTF-8";
        String url = OcrApi;
        String headers = "";
        String date = "";

        StringBuffer sb = new StringBuffer();
        sb.append(method).append("\n").append(accept).append("\n").append(contentMD5).append("\n")
                .append(contentType).append("\n").append(date).append("\n");
        if ("".equals(headers)) {
            sb.append(headers).append(url);
        } else {
            sb.append(headers).append("\n").append(url);
        }

        //构建参与请求签名计算的明文
        String plaintext = sb.toString();
        //计算请求签名值
        String reqSignature = doSignatureBase64(plaintext, RealnameVerifyConstant.APPKEY);

        //查询时间戳(精确到毫秒)
        long timeStamp = timeStamp();

        //构建请求头
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("X-Tsign-Open-App-Id", RealnameVerifyConstant.APPID);
        header.put("X-Tsign-Open-Auth-Mode", "Signature");
        header.put("X-Tsign-Open-Ca-Timestamp", String.valueOf(timeStamp));
        header.put("Accept", accept);
        header.put("Content-Type", contentType);
        header.put("X-Tsign-Open-Ca-Signature", reqSignature);
        header.put("Content-MD5", contentMD5);

        //发送POST请求
        String result;
        switch (httpMethod){

            case POST:
                result = HttpUtil.sendPOST(OcrUrl, reqBodyData, header, "UTF-8");
                break;

            case GET:
                HashMap<String, Object> param = JSON.parseObject(reqBodyData, HashMap.class);
                result = HttpUtil.sendGet(OcrUrl, param, header);
                break;

            default:
                log.info("请求方法不存在");
                return null;
        }

        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson == null){
            return null;
        }

        int code = resultJson.getInteger("code");
        if (code != 0) {
            String msg = resultJson.getString("message");
            log.error("实名认证失败：" + msg);
            return null;
        }

        JSONObject data = resultJson.getJSONObject("data");
        log.info("请求返回信息： " + resultJson.toString());

        return data;
    }

    public static boolean checkPass(HttpServletRequest request, String rbody, String appSecret) throws Exception {

        String signture = request.getHeader("X-Tsign-Open-SIGNATURE");
        //1. 查询时间戳的字节流
        String timestamp = request.getHeader("X-Tsign-Open-TIMESTAMP");
//		String content_type  =request
        //2. 查询query请求字符串
        String requestQuery = getRequestQueryStr(request);
        //4、按照规则进行加密
        String signdata = timestamp + requestQuery + rbody;
        String mySignature = getSignature(signdata, appSecret, "HmacSHA256", "UTF-8");
        log.info("加密出来的签名值：----------->>>>>>" + mySignature);
        log.info("header里面的签名值：---------->>>>>>" + signture);
        if (mySignature.equals(signture)) {
            log.info("校验通过");
            return true;
        } else {
            log.info("校验失败");
            return false;
        }

    }

    /**
     * 查询请求body
     *
     * @param request
     * @param encoding
     * @return
     */
    public static String getRequestBody(HttpServletRequest request, String encoding) throws IOException {
        // 请求内容RequestBody
        String reqBody = null;
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];

        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }

        return new String(buffer, encoding);
    }

    /**
     * 查询query请求字符串
     *
     * @param request
     * @return
     */
    public static String getRequestQueryStr(HttpServletRequest request) {
        //对 Query 参数按照字典对 Key 进行排序后,按照value1+value2方法拼接
        //转换一下数据类型并排序
        List<String> req_List = new ArrayList();
        Enumeration<String> reqEnu = request.getParameterNames();
        while (reqEnu.hasMoreElements()) {
            req_List.add(reqEnu.nextElement());
        }
        Collections.sort(req_List);
        String requestQuery = "";
        for (String key : req_List) {
            String value = request.getParameter(key);
            requestQuery += value == null ? "" : value;
        }
        log.info("查询的query请求字符串是：------》》》" + requestQuery);
        return requestQuery;
    }

    /***
     * 查询请求签名值
     *
     * @param data
     *            加密前数据
     * @param key
     *            密钥
     * @param algorithm
     *            HmacMD5 HmacSHA1 HmacSHA256 HmacSHA384 HmacSHA512
     * @param encoding
     *            编码格式
     * @return HMAC加密后16进制字符串
     * @throws Exception
     */
    public static String getSignature(String data, String key, String algorithm, String encoding) throws Exception {

        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(encoding), algorithm);
        mac.init(secretKey);
        mac.update(data.getBytes(encoding));
        return byte2hex(mac.doFinal());
    }

    /***
     * 将byte[]转成16进制字符串
     *
     * @param data
     *
     * @return 16进制字符串
     */
    public static String byte2hex(byte[] data) {
        StringBuilder hash = new StringBuilder();
        String stmp;
        for (int n = 0; data != null && n < data.length; n++) {
            stmp = Integer.toHexString(data[n] & 0XFF);
            if (stmp.length() == 1)
                hash.append('0');
            hash.append(stmp);
        }
        return hash.toString();
    }

}
