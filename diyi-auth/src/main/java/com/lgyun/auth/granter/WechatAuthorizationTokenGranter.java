package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.enums.BladeUserEnum;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.WechatConstant;
import com.lgyun.common.exception.ServiceException;
import com.lgyun.common.tool.AesCbcUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.HttpUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class WechatAuthorizationTokenGranter implements ITokenGranter {
    private static Logger logger = LoggerFactory.getLogger(WechatAuthorizationTokenGranter.class);

    public static final String GRANT_TYPE = "wechat_authorization";

    private IUserClient userClient;

    @Override
    public UserInfo grant(TokenParameter tokenParameter) {

        try {
            // 获取微信授权码
            String wechatCode = tokenParameter.getArgs().getStr("wechatCode");

            Map<String, String> requestUrlParam = new HashMap<>();
            requestUrlParam.put("grant_type", "authorization_code");    //默认参数
            requestUrlParam.put("appid", WechatConstant.WECHAT_APPID);    //开发者设置中的appId
            requestUrlParam.put("secret", WechatConstant.WECHAT_SECRET);    //开发者设置中的appSecret
            requestUrlParam.put("js_code", wechatCode);    //小程序调用wx.login返回的code

            JSONObject jsonObject = JSON.parseObject(HttpUtil.post(WechatConstant.WECHAT_SESSIONHOST, requestUrlParam));
            if (jsonObject == null) {
                logger.error("微信授权失败, 获取数据失败");
                throw new ServiceException("微信授权失败");
            }

            Object errcode = jsonObject.get("errcode");
            String errmsg = String.valueOf(jsonObject.get("errmsg"));
            if (errcode != null) {
                logger.error(errmsg);
                throw new ServiceException(errmsg);
            }

            String openid = String.valueOf(jsonObject.get("openid"));
            String sessionKey = jsonObject.getString("session_key");
            if (StringUtils.isBlank(openid)) {
                logger.error("微信授权失败, 返回参数缺失openid");
                throw new ServiceException("微信授权失败");
            }

            if (StringUtils.isBlank(sessionKey)) {
                logger.error("微信授权失败, 返回参数缺失session_key");
                throw new ServiceException("微信授权失败");
            }

            // 获取加密算法的初始向量
            String iv = tokenParameter.getArgs().getStr("iv");
            // 获取加密数据
            String encryptedData = tokenParameter.getArgs().getStr("encryptedData");
            // 参数含义：第一个，加密数据串（String）；第二个，session_key需要通过微信小程序的code获得（String）；
            // 第三个，数据加密时所使用的偏移量，解密时需要使用（String）；第四个，编码
            String AesResult = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");

            if (StringUtil.isBlank(AesResult)) {
                throw new ServiceException("解密失败");
            }

            // 将解密后的JSON格式字符串转化为对象
            jsonObject = JSONObject.parseObject(AesResult);
            // 获取手机号
            String purePhoneNumber = String.valueOf(jsonObject.get("purePhoneNumber"));
            // 微信授权登陆
            String tenantId = tokenParameter.getArgs().getStr("tenantId");
            User user = userClient.wechatAuthorization(openid, sessionKey, purePhoneNumber, tenantId);

            UserInfo userInfo = null;
            if (!(Func.isNull(user))) {
                // 获取用户类型
                String userType = tokenParameter.getArgs().getStr("userType");
                R<UserInfo> result;
                // 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
                if (userType.equals(BladeUserEnum.WEB.getName())) {
                    result = userClient.userInfo(user.getTenantId(), user.getAccount(), user.getPassword());
                } else if (userType.equals(BladeUserEnum.APP.getName())) {
                    result = userClient.userInfo(user.getTenantId(), user.getAccount(), user.getPassword());
                } else {
                    result = userClient.userInfo(user.getTenantId(), user.getAccount(), user.getPassword());
                }
                userInfo = result.isSuccess() ? result.getData() : null;
            }
            return userInfo;

        } catch (Exception e) {
            logger.error("系统异常：" + e);
            throw new ServiceException(e.getMessage());
        }
    }

}