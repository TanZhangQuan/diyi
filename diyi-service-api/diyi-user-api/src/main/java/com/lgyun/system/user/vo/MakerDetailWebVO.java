package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.VerifyStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户端创客详情VO
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class MakerDetailWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 认证状态
     */
    private CertificationState certificationState;

    /**
     * 自我描述
     */
    private String selfDesc;

    /**
     * 已接订单数
     */
    private Integer orderNum;

    /**
     * 总收入
     */
    private BigDecimal income;

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
     * 开户支行
     */
    private String subBankName;

    /**
     * 身份证验证状态：未验证，验证通过，验证未通过
     */
    private VerifyStatus idcardVerifyStatus;

    /**
     * 活体验证状态：未验证，验证通过，验证未通过
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
     * 平台加盟合同
     */
    private String makerJoinAgreement;

    /**
     * 授权委托书
     */
    private String makerPowerAttorney;

    /**
     * 注册日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    /**
     * 账户状态
     */
    private AccountState makerState;
}
