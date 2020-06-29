package com.lgyun.common.tool;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.RealnameVerifyConstant;
import com.lgyun.common.dto.ConfigParams;
import com.lgyun.common.dto.ContextInfo;
import com.lgyun.common.dto.IndivInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 实名认证工具
 *
 * @author tzq
 * @since 2020/6/27 18:00
 */
public class RealnameVerifyUtil {
    private static Logger logger = LoggerFactory.getLogger(RealnameVerifyUtil.class);

    /**
     * @return 返回token
     */
    public static String getToken() {
        logger.info("-----------获取Token------------");
        String res = RealnameVerifyHttpUtil.sendGet(RealnameVerifyConstant.getTokenUrl);
        JSONObject result = JSONObject.parseObject(res);
        int code = result.getInteger("code");
        if (code != 0) {
            return null;
        }
        JSONObject data = result.getJSONObject("data");
        String token = data.getString("token");
        logger.info("获得的token是:======== ");
        logger.info(token);
        logger.info("============================");
        return token;
    }

    /**
     * 身份证实名认证
     */
    public static R IdCardOCR(String infoImg, String emblemImg) {
        try {
            //获取实名认证token
            String token = getToken();
            if (StringUtil.isBlank(token)) {
                return R.fail("实名鉴权失败");
            }

            //整理请求参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("infoImg", infoImg);
            jsonObject.put("emblemImg", emblemImg);
            String params = JSONObject.toJSONString(jsonObject);

            //实名认证请求
            String res = RealnameVerifyHttpUtil.sendPost(RealnameVerifyConstant.idCardOCRUrl, token, params);
            JSONObject result = JSONObject.parseObject(res);
            int code = result.getInteger("code");
            String msg = result.getString("message");
            if (code != 0) {
                return R.fail(code, msg);
            }
            JSONObject data = result.getJSONObject("data");
            logger.info(data.toJSONString());

            return R.data(data);
        } catch (Exception e) {
            logger.error("实名认证异常", e);
        }
        return R.fail("实名认证失败");
    }

    /**
     * 刷脸实名认证
     */
    public static R faceOCR(Long contextId, String name, String certNo) {
        try {
            //指定页面显示认证方式
            List<String> availableAuthTypes = new ArrayList<>();
            availableAuthTypes.add("PSN_FACEAUTH_BYURL");

            //业务方交互上下文信息
            ContextInfo contextInfo = new ContextInfo();
            contextInfo.setContextId(String.valueOf(contextId));
            contextInfo.setNotifyUrl(RealnameVerifyConstant.faceOCRNotifyUrl);

            //业务方交互上下文信息
            IndivInfo indivInfo = new IndivInfo();
            indivInfo.setName(name);
            indivInfo.setCertNo(certNo);

            //认证配置信息
            String[] indivUneditableInfo = new String[]{"name", "certNo"};
            ConfigParams configParams = new ConfigParams();
            configParams.setIndivUneditableInfo(indivUneditableInfo);

            //刷脸实名认证请求
            return faceBankCardMobileOCR("PSN_FACEAUTH_BYURL", availableAuthTypes, contextInfo, indivInfo, configParams);

        } catch (Exception e) {
            logger.error("刷脸实名认证异常", e);
        }
        return R.fail("刷脸实名认证失败");
    }

    /**
     * 银行卡实名认证
     */
    public static R bankCardOCR(Long contextId, String name, String certNo, String bankCardNo, String mobileNo) {
        try {
            //指定页面显示认证方式
            List<String> availableAuthTypes = new ArrayList<>();
            availableAuthTypes.add("PSN_BANK4_AUTHCODE");

            //业务方交互上下文信息
            ContextInfo contextInfo = new ContextInfo();
            contextInfo.setContextId(String.valueOf(contextId));
            contextInfo.setNotifyUrl(RealnameVerifyConstant.bankCardOCRNotifyUrl);

            //业务方交互上下文信息
            IndivInfo indivInfo = new IndivInfo();
            indivInfo.setName(name);
            indivInfo.setCertNo(certNo);
            indivInfo.setBankCardNo(bankCardNo);
            indivInfo.setMobileNo(mobileNo);

            //认证配置信息
            String[] indivUneditableInfo = new String[]{"name", "certNo", "mobileNo", "bankCardNo"};
            ConfigParams configParams = new ConfigParams();
            configParams.setIndivUneditableInfo(indivUneditableInfo);

            //刷脸实名认证请求
            return faceBankCardMobileOCR("PSN_BANK4_AUTHCODE", availableAuthTypes, contextInfo, indivInfo, configParams);

        } catch (Exception e) {
            logger.error("刷脸实名认证异常", e);
        }
        return R.fail("刷脸实名认证失败");
    }

    /**
     * 手机号实名认证
     */
    public static R mobileOCR(Long contextId, String name, String certNo, String mobileNo) {
        try {
            //指定页面显示认证方式
            List<String> availableAuthTypes = new ArrayList<>();
            availableAuthTypes.add("PSN_TELECOM_AUTHCODE");

            //业务方交互上下文信息
            ContextInfo contextInfo = new ContextInfo();
            contextInfo.setContextId(String.valueOf(contextId));
            contextInfo.setNotifyUrl(RealnameVerifyConstant.mobileOCRNotifyUrl);

            //业务方交互上下文信息
            IndivInfo indivInfo = new IndivInfo();
            indivInfo.setName(name);
            indivInfo.setCertNo(certNo);
            indivInfo.setMobileNo(mobileNo);

            //认证配置信息
            String[] indivUneditableInfo = new String[]{"name", "certNo", "mobileNo"};
            ConfigParams configParams = new ConfigParams();
            configParams.setIndivUneditableInfo(indivUneditableInfo);

            //刷脸实名认证请求
            return faceBankCardMobileOCR("PSN_TELECOM_AUTHCODE", availableAuthTypes, contextInfo, indivInfo, configParams);

        } catch (Exception e) {
            logger.error("刷脸实名认证异常", e);
        }
        return R.fail("刷脸实名认证失败");
    }

    /**
     * 人脸识别认证、银行卡认证以及手机认证
     */
    public static R faceBankCardMobileOCR(String authType, List<String> availableAuthTypes, ContextInfo contextInfo, IndivInfo indivInfo, ConfigParams configParams) {

        //获取实名认证token
        String token = getToken();
        if (StringUtil.isBlank(token)) {
            return R.fail("实名鉴权失败");
        }

        //整理请求参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authType", authType);
        jsonObject.put("availableAuthTypes", availableAuthTypes);
        jsonObject.put("contextInfo", contextInfo);
        jsonObject.put("indivInfo", indivInfo);
        jsonObject.put("configParams", configParams);

        //实名认证请求
        String res = RealnameVerifyHttpUtil.sendPost(RealnameVerifyConstant.faceBankCardMobileOCROCRUrl, token, jsonObject.toString());
        JSONObject result = JSONObject.parseObject(res);
        int code = result.getInteger("code");
        String msg = result.getString("message");
        if (code != 0) {
            return R.fail(code, msg);
        }
        JSONObject data = result.getJSONObject("data");
        logger.info("实名认证结果：", data);

        return R.data(data);
    }
}
