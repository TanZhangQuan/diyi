package com.lgyun.common.constant;

/**
 * AppConstant
 *
 * @author liangfeihu
 * @since 2020/6/2 23:32
 */
public interface SmsConstant {

    //短信配置
    // 短信平台(云通讯:yuntongxun)
    String SMS_PLATFORM = "yuntongxun";
    // 短信发送时间间隔前缀
    String SEND_INTERVAL = "send_interval_";
    // 短信有效期前缀
    String AVAILABLE_TIME = "available_time_";
    // 短信时间段内最大发送次数前缀
    String MAX_SEND_TIME = "max_send_time_";
    // 短信有效期
    Integer SMS_AVAILABLE_TIME_MINUTES = 5;
    // 短信发送时间间隔
    Integer SMS_SEND_INTERVAL_MINUTES = 1;
    // 时间段内短信最大发送次数, 单位秒
    Long SMS_MAX_SEND_TIME = 86400L;
    // 时间段内短信最大发送次数
    Integer SMS_MAX_SEND_NUMBER = 50;

    //云通讯短信配置
    // 请求URl
    String YUNTONGXUN_SMS_URL = "app.cloopen.com";
    // 请求端口
    String YUNTONGXUN_SMS_PORT = "8883";
    // 账号ACCOUNT SID
    String YUNTONGXUN_SMS_ACCOUNTSID = "8a216da870e2267e01711fe50c8d2283";
    // 账号令牌AUTH TOKEN
    String YUNTONGXUN_SMS_ACCOUNTTOKEN = "a6b013e995fa4edb93bf68aee2f99087";
    // 账号APPID
    String YUNTONGXUN_SMS_APPID = "8a216da870e2267e01711fe50cea228a";
    // 云通讯短信模板ID
    String TEMPLATE_CODE_ID = "595738";
    // 云通讯短信模板ID
    String TEMPLATE_LINK_ID = "595738";

}
