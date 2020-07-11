package com.lgyun.common.tool;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.UserType;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;


/**
 * @Description 云通讯短信发送工具类
 * @Author tzq
 * @Date 2020.03.30
 */
@Component
@AllArgsConstructor
public class YunTongXunSmsUtil {
    private static Logger logger = LoggerFactory.getLogger(YunTongXunSmsUtil.class);

    private RedisUtil redisUtil;

    /**
     * 云通讯发送短信
     *
     * @param mobile 手机号
     */
    public R send(String[] datas, String mobile, UserType userType) {

        logger.info("========云通讯短信发送=========", mobile);
        String cacheKey = userType.getValue() + SmsConstant.MAX_SEND_TIME + mobile;
        int maxNum = 0;
        if (redisUtil.get(cacheKey) != null) {
            maxNum = (Integer) redisUtil.get(cacheKey);

            //单日短信最大发送次数限制
            if (maxNum >= SmsConstant.SMS_MAX_SEND_NUMBER) {
                logger.info(mobile, "===============单日短信达到最大发送次数=============", maxNum);
                return R.fail("单日短信达到最大发送次数");
            }

        }

        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(SmsConstant.YUNTONGXUN_SMS_URL, SmsConstant.YUNTONGXUN_SMS_PORT);
        sdk.setAccount(SmsConstant.YUNTONGXUN_SMS_ACCOUNTSID, SmsConstant.YUNTONGXUN_SMS_ACCOUNTTOKEN);
        sdk.setAppId(SmsConstant.YUNTONGXUN_SMS_APPID);
        sdk.setBodyType(BodyType.Type_JSON);

        HashMap<String, Object> result = sdk.sendTemplateSMS(mobile, SmsConstant.TEMPLATE_ID, datas);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                logger.info(key + " = " + object);
            }

            //记录短信发送次数
            long time = redisUtil.getExpire(cacheKey);
            if (time <= 0L) {
                time = SmsConstant.SMS_MAX_SEND_TIME;
            }

            redisUtil.set(cacheKey, (maxNum + 1), time);
            return R.success("验证码发送成功");
        } else {
            //异常返回输出错误码和错误信息
            logger.error("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }

        return R.fail("验证码发送失败");
    }
}
