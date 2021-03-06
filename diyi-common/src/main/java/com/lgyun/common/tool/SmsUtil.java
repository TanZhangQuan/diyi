package com.lgyun.common.tool;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.CodeType;
import com.lgyun.common.enumeration.MessageType;
import com.lgyun.common.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 短信验证码 Util
 *
 * @author tzq
 * @since 2020/8/5 23:07
 */
@Slf4j
@Component
@AllArgsConstructor
public class SmsUtil {

    private RedisUtil redisUtil;
    private YunTongXunSmsUtil yunTongXunSmsUtil;

    /**
     * 发送验证码
     *
     * @param mobile
     * @param userType
     * @return
     */
    public R<String> sendCode(String mobile, UserType userType, CodeType codeType) {

        String availableKey = SmsConstant.AVAILABLE_TIME + mobile + "_" + userType + "_" + codeType;
        String sendKey = SmsConstant.SEND_INTERVAL_CODE + mobile + "_" + userType + "_" + codeType;

        // 判断发送间隔是否正常
        if (redisUtil.get(sendKey) != null) {
            return R.fail("短信已经发送，请稍后重试");
        }

        //随机六位数字验证码
        String randomCode = RandomStringUtils.randomNumeric(6);
        log.info("手机号：{}, 验证码：{}", mobile, randomCode);

        if ("yuntongxun".equals(SmsConstant.SMS_PLATFORM)) {
            String[] datas = new String[]{randomCode, String.valueOf(SmsConstant.SMS_AVAILABLE_TIME_MINUTES)};
            R<String> response = yunTongXunSmsUtil.send(datas, mobile, MessageType.CODE);
            if (!(response.isSuccess())) {
                return response;
            }

            redisUtil.set(availableKey, randomCode, SmsConstant.SMS_AVAILABLE_TIME_MINUTES, TimeUnit.MINUTES);
            redisUtil.set(sendKey, true, SmsConstant.SMS_SEND_INTERVAL_MINUTES, TimeUnit.MINUTES);
            return response;

        } else {
            return R.fail("短信平台不存在");
        }

    }

    /**
     * 发送链接
     *
     * @param mobile
     * @param link
     * @return
     */
    public R<String> sendLink(String mobile, String link, UserType userType, MessageType messageType) {

        String sendKey = SmsConstant.SEND_INTERVAL_LINK + mobile + "_" + userType.getValue() + "_" + messageType;

        // 判断发送间隔是否正常
        if (redisUtil.get(sendKey) != null) {
            return R.fail("短信已经发送，请稍后重试");
        }

        if ("yuntongxun".equals(SmsConstant.SMS_PLATFORM)) {
            String[] datas = new String[]{link};
            R<String> response = yunTongXunSmsUtil.send(datas, mobile, messageType);
            if (!(response.isSuccess())) {
                return response;
            }

            redisUtil.set(sendKey, true, SmsConstant.SMS_SEND_INTERVAL_MINUTES, TimeUnit.MINUTES);
            return response;

        } else {
            return R.fail("短信平台不存在");
        }

    }
}
