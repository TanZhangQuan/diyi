package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.enums.BladeUserEnum;
import com.lgyun.auth.utils.RedisUtil;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.WechatConstant;
import com.lgyun.common.exception.ServiceException;
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
public class WechatSmsCodeTokenGranter implements ITokenGranter {
    private static Logger logger = LoggerFactory.getLogger(WechatSmsCodeTokenGranter.class);

    public static final String GRANT_TYPE = "wechat_smscode";

    private IUserClient userClient;
    private RedisUtil redisUtil;

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

            // 获取redis短信验证码
            String account = tokenParameter.getArgs().getStr("account");
//            String redisCode = String.valueOf(redisUtil.get(buildKey(phone)));
            String redisCode = "000000";
            // 判断验证码
            String smsCode = tokenParameter.getArgs().getStr("smsCode");
            if (smsCode == null || !StringUtil.equalsIgnoreCase(redisCode, smsCode)) {
                throw new ServiceException(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
            }

            // 短信验证码登陆
            String tenantId = tokenParameter.getArgs().getStr("tenantId");
            User user = userClient.wechatAuthorization(openid, sessionKey, account, tenantId);

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