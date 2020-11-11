package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.VerifyStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创客列表VO
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class MakerListWebVO implements Serializable {
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
     * 银行卡号
     */
    private String bankCardNo;

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
     * 是否个体户
     */
    private boolean boolIndividualBusiness;

    /**
     * 是否个独
     */
    private boolean boolIndividualEnterprise;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}