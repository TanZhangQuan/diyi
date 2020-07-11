package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.cache.CacheNames;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.constant.WechatConstant;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.tool.*;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * PasswordTokenGranter
 *
 * @author liangfeihu
 * @since 2020/6/6 01:02
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {
    private static Logger logger = LoggerFactory.getLogger(PasswordTokenGranter.class);

    public static final String GRANT_TYPE = "PASSWORD";

    private TokenUtil tokenUtil;
    private IUserClient userClient;
    private RedisUtil redisUtil;

    @Override
    public R grant(TokenParameter tokenParameter) throws Exception {
        //获取账号
        String account = tokenParameter.getArgs().getStr("account");
        //获取密码
        String password = tokenParameter.getArgs().getStr("password");
        //获取用户类型
        UserType userType = (UserType) tokenParameter.getArgs().get("userType");
        UserInfo userInfo;
        switch (userType) {
            case MAKER:
                // 获取微信授权码
                String wechatCode = tokenParameter.getArgs().getStr("wechatCode");
                if (StringUtil.isBlank(wechatCode)) {
                    return R.fail("请输入微信授权码");
                }

                Map<String, String> requestUrlParam = new HashMap<>();
                requestUrlParam.put("grant_type", "authorization_code");    //默认参数
                requestUrlParam.put("appid", WechatConstant.WECHAT_APPID);    //开发者设置中的appId
                requestUrlParam.put("secret", WechatConstant.WECHAT_SECRET);    //开发者设置中的appSecret
                requestUrlParam.put("js_code", wechatCode);    //小程序调用wx.login返回的code

                JSONObject jsonObject = JSON.parseObject(HttpUtil.post(WechatConstant.WECHAT_SESSIONHOST, requestUrlParam));
                if (jsonObject == null) {
                    logger.error("微信授权失败, 获取数据失败");
                    return R.fail("登陆失败");
                }

                Object errcode = jsonObject.get("errcode");
                String errmsg = jsonObject.getString("errmsg");
                if (errcode != null) {
                    logger.error(errmsg);
                    return R.fail("登陆失败");
                }

                String openid = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("session_key");
                if (StringUtils.isBlank(openid)) {
                    logger.error("微信授权失败, 返回参数缺失openid");
                    return R.fail("登陆失败");
                }

                if (StringUtils.isBlank(sessionKey)) {
                    logger.error("微信授权失败, 返回参数缺失session_key");
                    return R.fail("登陆失败");
                }

                //创客处理
                R res = userClient.makerSaveOrUpdate(openid, sessionKey, account, DigestUtil.encrypt(password), GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }

                userInfo = userClient.userInfoByPhone(account, userType);
                break;

            case ADMIN:
                //判断是否跑图形验证码
                if (CommonConstant.BOOL_GRAPH_CODE) {
                    HttpServletRequest request = WebUtil.getRequest();
                    String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
                    String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
                    // 获取验证码
                    String redisCode = String.valueOf(redisUtil.get(CacheNames.CAPTCHA_KEY + key));
                    // 判断验证码
                    if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
                        return R.fail(TokenUtil.CAPTCHA_NOT_CORRECT);
                    }
                }

                userInfo = userClient.userInfo(account, DigestUtil.encrypt(password), userType);
                break;

            default:
                return R.fail("用户类型有误");
        }

        if (userInfo == null) {
            if (UserType.ADMIN.equals(userType)) {
                return R.fail("账号或密码错误");
            } else {
                return R.fail("登陆失败");
            }
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        return R.data(authInfo);
    }

}
