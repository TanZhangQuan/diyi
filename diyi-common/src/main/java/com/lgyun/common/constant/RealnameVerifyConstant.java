package com.lgyun.common.constant;

public interface RealnameVerifyConstant {

    //百度身份证OCR的请求地址
    String BAIDUOCRURL = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";

    //百度身份证OCR的API Key
    String BAIDUOCRAPIKEY = "eLl37gBIFrwG5brOvo2Agpw3";

    //百度身份证OCR请求地址
    String BAIDUOCRSECRETKEY = "iQRvEGVOGdvYbm7COBPVa7a56SOxvVCZ";

    //实名认证应用id
    String APPID = "4438778216";

    //实名认证密钥
    String APPKEY = "aa9194b8dff42ad45f7dc864ca3dc281";

    //实名认证请求域名
    String HOST = "https://smlopenapi.esign.cn";
//    String HOST = "https://openapi.esign.cn";

    //活体认证、银行卡认证以及手机认证请求url
    String FACEBANKCARDMOBILEOCROCRURL = "/v2/identity/auth/web/indivAuthUrl";

    //活体认证回调url
    String FACEOCRNOTIFYURL = "https://wxtest.diyicr.com/diyi-user/maker/real-name-authentication/face-ocr-notify";

    //手机认证回调url
    String MOBILEOCRNOTIFYURL = "https://wxtest.diyicr.com/diyi-user/maker/real-name-authentication/mobile-ocr-notify";

    //银行卡认证回调url
    String BANKCARDOCRNOTIFYURL = "https://wxtest.diyicr.com/diyi-user/maker/real-name-authentication/bank-card-ocr-notify";

}
