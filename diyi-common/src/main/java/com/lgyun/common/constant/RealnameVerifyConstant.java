package com.lgyun.common.constant;

public interface RealnameVerifyConstant {

    //实名认证应用id
    String appId = "4438763774";

    //实名认证密钥
    String secret = "172779a80fd8629ef07e7edf71839fb7";

    //实名认证grantType
    String grantType = "client_credentials";

    //实名认证请求域名
    String host = "https://smlopenapi.esign.cn";
//    String host = "https://openapi.esign.cn";

    //获得Token请求url
    String getTokenUrl = host + "/v1/oauth2/access_token?appId=" + appId + "&secret=" + secret + "&grantType=" + grantType;

    //身份证实名认证请求url
    String idCardOCRUrl = host + "/v2/identity/auth/api/ocr/idcard";

    //人脸识别认证、银行卡认证以及手机认证请求url
    String faceBankCardMobileOCROCRUrl = host + "/v2/identity/auth/web/indivAuthUrl";

    //人脸识别认证、银行卡认证以及手机认证回调url
    String faceOCRNotifyUrl = "https://www.baidu.com/";

    //人脸识别认证、银行卡认证以及手机认证回调url
    String bankCardOCRNotifyUrl = "https://www.baidu.com/";

    //人脸识别认证、银行卡认证以及手机认证回调url
    String mobileOCRNotifyUrl = "https://www.baidu.com/";

}
