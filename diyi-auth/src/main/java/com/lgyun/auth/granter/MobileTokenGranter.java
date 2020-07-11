package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.constant.WechatConstant;
import com.lgyun.common.enumeration.CodeType;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.tool.HttpUtil;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.common.tool.SmsUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liangfeihu
 * @since 2020/6/30 18:27
 */
@Slf4j
@Component
@AllArgsConstructor
public class MobileTokenGranter implements ITokenGranter {
    private static Logger logger = LoggerFactory.getLogger(MobileTokenGranter.class);

    public static final String GRANT_TYPE = "MOBILE";

    private IUserClient userClient;
    private TokenUtil tokenUtil;
    private RedisUtil redisUtil;
    private SmsUtil smsUtil;

    /**
     * 获取用户信息
     *
     * @param tokenParameter 授权参数
     * @return UserInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R grant(TokenParameter tokenParameter) throws Exception {

        //获取用户类型
        UserType userType = (UserType) tokenParameter.getArgs().get("userType");
        //获取手机号
        String mobile = tokenParameter.getArgs().getStr("mobile");
        //获取用户填写的短信验证码
        String smsCode = tokenParameter.getArgs().getStr("smsCode");
        //获取缓存短信验证码
        String redisCode = (String) redisUtil.get(userType.getValue() + SmsConstant.AVAILABLE_TIME + mobile);
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, smsCode)) {
            return R.fail(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
        }

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

                String openid = "987654321";
                String sessionKey = "987654321";
                if (StringUtils.isBlank(openid)) {
                    logger.error("微信授权失败, 返回参数缺失openid");
                    return R.fail("登陆失败");
                }

                if (StringUtils.isBlank(sessionKey)) {
                    logger.error("微信授权失败, 返回参数缺失session_key");
                    return R.fail("登陆失败");
                }
                // 创客处理
                R res = userClient.makerSaveOrUpdate(openid, sessionKey, mobile, "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case ADMIN:
                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.userInfoByPhone(mobile, userType);
        if (userInfo == null) {
            if (UserType.ADMIN.equals(userType)) {
                return R.fail("管理员不存在");
            } else {
                return R.fail("登陆失败");
            }
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        //删除缓存短信验证码
        redisUtil.del(userType.getValue() + SmsConstant.AVAILABLE_TIME + mobile);

        return R.data(authInfo);
    }

    /**
     * 发送验证码
     * <p>
     * 1. 先去redis 查询是否 60S内已经发送
     * 2. 未发送： 判断手机号是否存 ? false :产生4位数字  手机号-验证码
     * 3. 发往消息中心-》发送信息
     * 4. 保存redis
     *
     * @param mobile 手机号
     * @return true、false
     */
    public R sendSmsCode(String mobile, CodeType codeType, UserType userType) {

        if (CodeType.LOGIN.equals(codeType) || CodeType.UPDATEPASSWORD.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (!(userClient.userInfoByPhone(mobile, userType) != null)) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case MAKER:
                    if (userClient.makerFindByPhone(mobile) == null) {
                        return R.fail("手机号未注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else if (CodeType.REGISTER.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (userClient.userInfoByPhone(mobile, userType) != null) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case MAKER:
                    if (userClient.makerFindByPhone(mobile) != null) {
                        return R.fail("手机号已注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else {
            return R.fail("验证码类型有误");
        }

        return smsUtil.sendCode(mobile, userType);
    }

}
