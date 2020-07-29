package com.lgyun.auth.granter;

import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * RegisterGranter
 *
 * @author liangfeihu
 * @since 2020/6/6 01:02
 */
@Slf4j
@Component
@AllArgsConstructor
public class RegisterGranter implements ITokenGranter {

    public static final String GRANT_TYPE = "REGISTER";

    private IUserClient userClient;
    private RedisUtil redisUtil;

    @Override
    public R grant(TokenParameter tokenParameter) {
        //获取用户类型
        UserType userType = (UserType) tokenParameter.getArgs().get("userType");
        //获取手机号
        String mobile = tokenParameter.getArgs().getStr("mobile");
        //获取密码
        String password = tokenParameter.getArgs().getStr("password");
        //获取用户填写的短信验证码
        String smsCode = tokenParameter.getArgs().getStr("smsCode");
        //获取缓存短信验证码
        String redisCode = (String) redisUtil.get(SmsConstant.AVAILABLE_TIME + mobile);
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, smsCode)) {
            return R.fail(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
        }

        switch (userType) {
            case MAKER:
                // 创客处理
                R res = userClient.makerDeal("", "", mobile, DigestUtil.encrypt(password), GrantType.REGISTER);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        return R.success("注册成功");
    }

}
