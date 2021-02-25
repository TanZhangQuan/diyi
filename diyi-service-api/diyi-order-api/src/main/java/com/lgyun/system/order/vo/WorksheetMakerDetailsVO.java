package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class WorksheetMakerDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetMakerId;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String idcardNo;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "身份证验证状态")
    private VerifyStatus idcardVerifyStatus;

    @ApiModelProperty(value = "活体验证状态")
    private VerifyStatus faceVerifyStatus;

    @ApiModelProperty(value = "银行卡验证状态")
    private VerifyStatus bankCardVerifyStatus;

    @ApiModelProperty(value = "手机号验证状态")
    private VerifyStatus phoneNumberVerifyStatus;

    @ApiModelProperty(value = "短视频审核状态")
    private VideoAudit videoAudit;

    @ApiModelProperty(value = "是否有有效的创客授权书")
    private Boolean boolPowerAttorney;

    @ApiModelProperty(value = "是否有有效的创客加盟协议")
    private Boolean boolJoinAgreement;

    @ApiModelProperty(value = "工单创客的状态")
    private WorksheetMakerState worksheetMakerState;

    @ApiModelProperty(value = "工作成果说明")
    private String achievementDesc;

    @ApiModelProperty(value = "工作成果")
    private String achievementFiles;

    @ApiModelProperty(value = "提交工作成果时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date achievementDate;

    @ApiModelProperty(value = "验收金额")
    private BigDecimal checkMoney;
}
