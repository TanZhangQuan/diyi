package com.lgyun.common.constant;

public interface RealnameVerifyConstant {

    //实名认证应用id
    String APPID = "4438778216";

    //实名认证密钥
    String APPKEY = "aa9194b8dff42ad45f7dc864ca3dc281";

    //实名认证请求域名
    String HOST = "https://smlopenapi.esign.cn";
//    String HOST = "https://openapi.esign.cn";

    //身份证识别信息请求url
    String IDCARDOCRURL = "/v2/identity/auth/api/ocr/idcard";

    //身份证验证请求url
    String IDCARDVERIFYURL = "/v2/identity/verify/individual/base";

    //手机号验证请求url
    String MOBILEVERIFYURL = "/v2/identity/verify/individual/telecom3Factors";

    //银行卡验证请求url
    String BANKCARDVERIFYURL = "/v2/identity/verify/individual/bank3Factors";

    //活体认证请求url
    String FACEVERIFYURL = "/v2/identity/auth/web/indivAuthUrl";

    //活体认证回调url
    String FACEVERIFYNOTIFYURL = "https://wxtest.diyicr.com/diyi-user/maker/real-name-authentication/face-ocr-notify";

}
