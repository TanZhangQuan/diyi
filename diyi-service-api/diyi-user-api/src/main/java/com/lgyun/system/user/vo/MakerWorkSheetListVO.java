package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.enumeration.VideoAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "创客列表VO")
public class MakerWorkSheetListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "身份证验证状态")
    private VerifyStatus idcardVerifyStatus;

    @ApiModelProperty(value = "活体验证状态")
    private VerifyStatus faceVerifyStatus;

    @ApiModelProperty(value = "银行卡验证状态")
    private VerifyStatus bankCardVerifyStatus;

    @ApiModelProperty(value = "手机号码验证状态")
    private VerifyStatus phoneNumberVerifyStatus;

    @ApiModelProperty(value = "是否有有效的创客授权书")
    private Boolean boolPowerAttorney;

    @ApiModelProperty(value = "是否有有效的创客加盟协议")
    private Boolean boolJoinAgreement;

    @ApiModelProperty(value = "视频状态")
    private VideoAudit videoAudit;

    @ApiModelProperty(value = "是否有商户和创客的补充协议")
    private boolean boolSupplementSign;

}
