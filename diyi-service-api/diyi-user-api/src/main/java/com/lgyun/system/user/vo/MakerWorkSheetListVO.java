package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.enumeration.VideoAudit;
import lombok.Data;

import java.io.Serializable;

/**
 * 创客列表VO
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class MakerWorkSheetListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 身份证验证状态：未验证，验证通过，验证未通过
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

    /**
     * 授权合同签署状态
     */
    private SignState empowerSignState;

    /**
     * 加盟合同签署状态
     */
    private SignState joinSignState;

    /**
     * 视频状态
     */
    private VideoAudit videoAudit;

    /**
     * 是否有商户和创客的补充协议
     */
    private boolean boolSupplementSign;

}
