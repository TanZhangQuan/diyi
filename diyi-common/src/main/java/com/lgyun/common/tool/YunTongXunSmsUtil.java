package com.lgyun.common.tool;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.MessageType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;


/**
 * @Description 云通讯短信发送工具类
 * @Author tzq
 * @Date 2020.03.30
 */
@Slf4j
@Component
@AllArgsConstructor
public class YunTongXunSmsUtil {

    private RedisUtil redisUtil;

    /**
     * 云通讯发送短信验证码/短信链接
     *
     * @param mobile 手机号
     */
    public R<String> send(String[] datas, String mobile, MessageType messageType) {

        log.info("========云通讯短信发送=========", mobile);
        String cacheKey = SmsConstant.MAX_SEND_TIME + mobile;
        int maxNum = 0;
        if (redisUtil.get(cacheKey) != null) {
            maxNum = (Integer) redisUtil.get(cacheKey);

            //单日短信最大发送次数限制
            if (maxNum >= SmsConstant.SMS_MAX_SEND_NUMBER) {
                log.info(mobile, "===============单日短信达到最大发送次数=============", maxNum);
                return R.fail("单日短信达到最大发送次数");
            }

        }

        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(SmsConstant.YUNTONGXUN_SMS_URL, SmsConstant.YUNTONGXUN_SMS_PORT);
        sdk.setAccount(SmsConstant.YUNTONGXUN_SMS_ACCOUNTSID, SmsConstant.YUNTONGXUN_SMS_ACCOUNTTOKEN);
        sdk.setAppId(SmsConstant.YUNTONGXUN_SMS_APPID);
        sdk.setBodyType(BodyType.Type_JSON);

        String template_id;
        switch (messageType){
            case CODE:
                template_id = SmsConstant.TEMPLATE_CODE_ID;
                break;

            case LINK:
                template_id = SmsConstant.TEMPLATE_LINK_ID;
                break;

            default:
                return R.fail("短信类型不存在");
        }

        HashMap<String, Object> result = sdk.sendTemplateSMS(mobile, template_id, datas);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                log.info(key + " = " + object);
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
            log.error("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }

        return R.fail("验证码发送失败");
    }

}
