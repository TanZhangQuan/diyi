package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.auth.utils.WechatUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.CodeType;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.common.tool.SmsUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 短信验证码登录
 *
 * @author liangfeihu
 * @since 2020/6/30 18:27
 */
@Slf4j
@Component
@AllArgsConstructor
public class MobileTokenGranter implements ITokenGranter {

    public static final String GRANT_TYPE = "MOBILE";

    private IUserClient userClient;
    private TokenUtil tokenUtil;
    private RedisUtil redisUtil;
    private SmsUtil smsUtil;
    private WechatUtil wechatUtil;

    /**
     * 短信验证码登录
     * 查询用户信息
     *
     * @param tokenParameter 授权参数
     * @return UserInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R grant(TokenParameter tokenParameter) throws Exception {

        //查询手机号
        String mobile = tokenParameter.getArgs().getStr("mobile");
        //查询用户填写的短信验证码
        String smsCode = tokenParameter.getArgs().getStr("smsCode");
        //查询缓存短信验证码
        String redisCode = (String) redisUtil.get(SmsConstant.AVAILABLE_TIME + mobile);
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, smsCode)) {
            return R.fail(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
        }

        //查询用户类型
        UserType userType = (UserType) tokenParameter.getArgs().get("userType");
        R<String> res;
        switch (userType) {

            case MAKER:
                // 微信授权码
                String wechatCode = tokenParameter.getArgs().getStr("wechatCode");
                // 微信授权
                R<JSONObject> result = wechatUtil.authorization(wechatCode);
                if (!(result.isSuccess())) {
                    return result;
                }
                JSONObject jsonObject = result.getData();
                String openid = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("sessionKey");
                // 创客处理
                res = userClient.makerDeal(openid, sessionKey, mobile, "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case ENTERPRISE:
                // 商户处理
                res = userClient.enterpriseWorkerDeal(mobile, "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case SERVICEPROVIDER:
                // 服务商处理
                res = userClient.serviceProviderWorkerDeal(mobile, "", GrantType.MOBILE);
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
            return R.fail("手机号未注册");
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);
        //删除缓存短信验证码
        redisUtil.del(SmsConstant.AVAILABLE_TIME + mobile);

        return R.data(authInfo);
    }

    /**
     * 发送短信验证码
     * <p>
     * 1. 先去redis 查询是否 60S内已经发送
     * 2. 未发送： 判断手机号是否存 ? false :产生4位数字  手机号-验证码
     * 3. 发往消息中心-》发送信息
     * 4. 保存redis
     *
     * @param mobile 手机号
     * @return true、false
     */
    public R<String> sendSmsCode(String mobile, CodeType codeType, UserType userType) {

        if (CodeType.LOGIN.equals(codeType) || CodeType.UPDATEPASSWORD.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (userClient.userByPhone(mobile) == null) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case ENTERPRISE:
                    if (userClient.enterpriseWorkerFindByPhoneNumber(mobile) == null) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case SERVICEPROVIDER:
                    if (userClient.serviceProviderWorkerFindByPhoneNumber(mobile) == null) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case MAKER:
                    if (userClient.makerFindByPhoneNumber(mobile) == null) {
                        return R.fail("手机号未注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else if (CodeType.REGISTER.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (userClient.userByPhone(mobile) != null) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case ENTERPRISE:
                    if (userClient.enterpriseWorkerFindByPhoneNumber(mobile) != null) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case SERVICEPROVIDER:
                    if (userClient.serviceProviderWorkerFindByPhoneNumber(mobile) != null) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case MAKER:
                    if (userClient.makerFindByPhoneNumber(mobile) != null) {
                        return R.fail("手机号已注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else {
            return R.fail("验证码类型有误");
        }

        return smsUtil.sendCode(mobile);
    }

}
