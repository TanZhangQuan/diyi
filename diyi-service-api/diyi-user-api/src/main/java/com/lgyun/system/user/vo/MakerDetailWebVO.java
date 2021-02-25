package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.VerifyStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "创客详情VO")
public class MakerDetailWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "认证状态")
    private CertificationState certificationState;

    @ApiModelProperty(value = "自我描述")
    private String selfDesc;

    @ApiModelProperty(value = "已接订单数")
    private Integer orderNum;

    @ApiModelProperty(value = "总收入")
    private BigDecimal income;

    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "开户支行")
    private String subBankName;

    @ApiModelProperty(value = "身份证验证状态")
    private VerifyStatus idcardVerifyStatus;

    @ApiModelProperty(value = "活体验证状态")
    private VerifyStatus faceVerifyStatus;

    @ApiModelProperty(value = "银行卡验证状态")
    private VerifyStatus bankCardVerifyStatus;

    @ApiModelProperty(value = "手机号码验证状态")
    private VerifyStatus phoneNumberVerifyStatus;

    @ApiModelProperty(value = "平台加盟合同")
    private String makerJoinAgreement;

    @ApiModelProperty(value = "授权委托书")
    private String makerPowerAttorney;

    @ApiModelProperty(value = "注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "账户状态")
    private AccountState makerState;
}
