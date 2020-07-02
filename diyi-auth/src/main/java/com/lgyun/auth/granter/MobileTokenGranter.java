package com.lgyun.auth.granter;

import com.lgyun.auth.enums.BladeUserEnum;
import com.lgyun.auth.utils.RedisUtil;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.exception.ServiceException;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author liangfeihu
 * @since 2020/6/30 18:27
 */
@Slf4j
@Component
@AllArgsConstructor
public class MobileTokenGranter implements ITokenGranter {

    public static final String GRANT_TYPE = "mobile";

    private IUserClient userClient;
    private RedisUtil redisUtil;

    /**
     * 获取用户信息
     *
     * @param tokenParameter 授权参数
     * @return UserInfo
     */
    @Override
    public UserInfo grant(TokenParameter tokenParameter) {
        // 获取用户输入短信验证码
        String code = tokenParameter.getArgs().getStr("code");
        // 获取redis短信验证码
        String phone = tokenParameter.getArgs().getStr("phone");
        String redisCode = String.valueOf(redisUtil.get(buildKey(phone)));
        // 判断验证码
        if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
            throw new ServiceException(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
        }

        UserInfo userInfo = null;
        if (Func.isNoneBlank(phone)) {
            // 获取用户类型
            String userType = tokenParameter.getArgs().getStr("userType");
            R<UserInfo> result;
            // 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
            if (userType.equals(BladeUserEnum.WEB.getName())) {
                result = userClient.userInfoByPhone(phone);
            } else if (userType.equals(BladeUserEnum.APP.getName())) {
                result = userClient.userInfoByPhone(phone);
            } else {
                result = userClient.userInfoByPhone(phone);
            }
            userInfo = result.isSuccess() ? result.getData() : null;
        }
        return userInfo;
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
    public R sendSmsCode(String mobile) {
        Object tempCode = redisUtil.get(buildKey(mobile));
        if (tempCode != null) {
            log.error("用户:{}验证码未失效{}", mobile, tempCode);
            return R.fail("验证码未失效，请失效后再次申请");
        }

        R<UserInfo> result = userClient.userInfoByPhone(mobile);
        if (!result.isSuccess()) {
            log.error("根据用户手机号{}查询用户为空", mobile);
            return R.fail("手机号不存在");
        }

        String code = RandomStringUtils.randomNumeric(4);
        // TODO 接入短信第三方信息
        log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
        redisUtil.set(buildKey(mobile), code, 5L, TimeUnit.MINUTES);
        return R.success("true");
    }

    private String buildKey(String deviceId) {
        return "mobile:oauth:" + deviceId;
    }

}
