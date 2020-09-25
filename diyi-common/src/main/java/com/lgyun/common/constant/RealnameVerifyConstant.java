package com.lgyun.common.constant;

public interface RealnameVerifyConstant {

    //实名认证应用id
    String APPID = "7438808738";

    //实名认证密钥
    String APPKEY = "7b0622dd4177498906f539960ba00afd";

    //实名认证请求域名
    String HOST = "https://smlopenapi.esign.cn";
//    String HOST = "https://openapi.esign.cn";

    //身份证实名认证请求url
    String IDCARDOCRURL = "/v2/identity/auth/api/ocr/idcard";

    //活体认证、银行卡认证以及手机认证请求url
    String FACEBANKCARDMOBILEOCROCRURL = "/v2/identity/auth/web/indivAuthUrl";

    //活体认证回调url
    String FACEOCRNOTIFYURL = "http://246953jp33.qicp.vip/diyi-user/maker/face_ocr_notify";
//    String FACEOCRNOTIFYURL = "https://wxtest.diyicr.com/diyi-user/maker/face_ocr_notify";

    //银行卡认证回调url
    String BANKCARDOCRNOTIFYURL = "http://246953jp33.qicp.vip/diyi-user/maker/bank_card_ocr_notify";
//    String BANKCARDOCRNOTIFYURL = "https://wxtest.diyicr.com/diyi-user/maker/bank_card_ocr_notify";

    //手机认证回调url
    String MOBILEOCRNOTIFYURL = "http://246953jp33.qicp.vip/diyi-user/maker/mobile_ocr_notify";
//    String MOBILEOCRNOTIFYURL = "https://wxtest.diyicr.com/diyi-user/maker/mobile_ocr_notify";

}
