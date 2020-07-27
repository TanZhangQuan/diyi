package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.VerifyStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 创客所有实名认证VO
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class MakerRealNameAuthenticationStateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 身份证实名认证状态
     */
    private VerifyStatus idcardVerifyStatus;

    /**
     * 人脸验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus faceVerifyStatus;

    /**
     * 银行卡验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus bankCardVerifyStatus;

    /**
     * 手机号码验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus phoneNumberVerifyStatus;

}
