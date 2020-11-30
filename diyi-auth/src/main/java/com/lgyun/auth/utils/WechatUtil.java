package com.lgyun.auth.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.WechatConstant;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.tool.AesCbcUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.HttpUtil;
import com.lgyun.common.tool.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信授权工具类
 *
 * @author tzq
 * @since 2020/6/6 00:21
 */
@Slf4j
@Component
public class WechatUtil {

    /**
     * 微信授权认证
     *
     * @param userType
     * @param code
     * @return
     * @throws Exception
     */
    public R<JSONObject> authorization(UserType userType, String code) throws Exception {
        return authorization(userType, code, null, null);
    }

    /**
     * 微信授权认证
     *
     * @param userType
     * @param code
     * @param iv
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public R<JSONObject> authorization(UserType userType, String code, String iv, String encryptedData) throws Exception {

        // 查询微信授权码
        if (StringUtil.isBlank(code)) {
            return R.fail("请输入微信授权码");
        }

        JSONObject result = new JSONObject();
        Map<String, String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("grant_type", "authorization_code");    //默认参数

        switch (userType) {

            case MAKER:

                requestUrlParam.put("appid", WechatConstant.MAKER_WECHAT_APPID);    //开发者设置中的appId
                requestUrlParam.put("secret", WechatConstant.MAKER_WECHAT_SECRET);    //开发者设置中的appSecret
                break;

            case PARTNER:

                requestUrlParam.put("appid", WechatConstant.PARTNER_WECHAT_APPID);    //开发者设置中的appId
                requestUrlParam.put("secret", WechatConstant.PARTNER_WECHAT_SECRET);    //开发者设置中的appSecret
                break;

            default:
                return R.fail("用户类型有误");
        }
        //小程序调用wx.login返回的code
        requestUrlParam.put("js_code", code);

        JSONObject jsonObject = JSON.parseObject(HttpUtil.post(WechatConstant.WECHAT_SESSIONHOST, requestUrlParam));
        if (jsonObject == null) {
            log.error("微信授权失败, 查询数据失败");
            return R.fail("登录失败");
        }

        Object errcode = jsonObject.get("errcode");
        String errmsg = jsonObject.getString("errmsg");
        if (errcode != null) {
            log.error(errmsg);
            return R.fail(errmsg);
        }

        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        if (StringUtils.isBlank(openid)) {
            log.error("微信授权失败, openid为空");
            return R.fail("登录失败");
        }

        if (StringUtils.isBlank(sessionKey)) {
            log.error("微信授权失败, session_key为空");
            return R.fail("登录失败");
        }

        result.put("openid", openid);
        result.put("sessionKey", sessionKey);

        if (Func.isNoneBlank(iv, encryptedData)) {
            // 参数含义：第一个，加密数据串（String）；第二个，session_key需要通过微信小程序的code获得（String）；
            // 第三个，数据加密时所使用的偏移量，解密时需要使用（String）；第四个，编码
            String AesResult = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");

            if (StringUtil.isBlank(AesResult)) {
                log.error("解密数据失败");
                return R.fail("登录失败");
            }

            // 将解密后的JSON格式字符串转化为对象
            jsonObject = JSONObject.parseObject(AesResult);
            // 查询手机号
            String purePhoneNumber = jsonObject.getString("purePhoneNumber");
            if (StringUtils.isBlank(purePhoneNumber)) {
                log.error("微信授权失败，手机号为空");
                return R.fail("登录失败");
            }

            result.put("purePhoneNumber", purePhoneNumber);
        }

        return R.data(result);
    }

}
