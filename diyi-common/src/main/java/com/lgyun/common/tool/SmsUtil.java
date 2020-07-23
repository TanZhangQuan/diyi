package com.lgyun.common.tool;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.MessageType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class SmsUtil {

    private RedisUtil redisUtil;
    private YunTongXunSmsUtil yunTongXunSmsUtil;

    /**
     * 发送验证码
     *
     * @param
     * @return
     */
    public R<String> sendCode(String mobile) {

        String sendKey = SmsConstant.SEND_INTERVAL + mobile;
        String availableKey = SmsConstant.AVAILABLE_TIME + mobile;

        // 判断发送间隔是否正常
        if (redisUtil.get(sendKey) != null) {
            return R.fail("短信已经发送，请稍后重试");
        }

        Random random = new Random();
        //随机六位数字验证码
        String randomCode = "";
        for (int i = 0; i < 6; i++) {
            randomCode += random.nextInt(10);
        }
        log.info("手机号：" + mobile + "，验证码：" + randomCode);

        if ("yuntongxun".equals(SmsConstant.SMS_PLATFORM)) {
            String[] datas = new String[]{randomCode, String.valueOf(SmsConstant.SMS_AVAILABLE_TIME_MINUTES)};
            R<String> response = yunTongXunSmsUtil.send(datas, mobile, MessageType.CODE);
            if (!(response.isSuccess())){
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
     * @param
     * @return
     */
    public R<String> sendLink(String mobile, String link) {

        String sendKey = SmsConstant.SEND_INTERVAL + mobile;

        // 判断发送间隔是否正常
        if (redisUtil.get(sendKey) != null) {
            return R.fail("短信已经发送，请稍后重试");
        }

        if ("yuntongxun".equals(SmsConstant.SMS_PLATFORM)) {
            String[] datas = new String[]{link};
            R<String> response = yunTongXunSmsUtil.send(datas, mobile, MessageType.LINK);
            if (!(response.isSuccess())){
                return response;
            }

            redisUtil.set(sendKey, true, SmsConstant.SMS_SEND_INTERVAL_MINUTES, TimeUnit.MINUTES);
            return response;

        } else {
            return R.fail("短信平台不存在");
        }

    }
}
