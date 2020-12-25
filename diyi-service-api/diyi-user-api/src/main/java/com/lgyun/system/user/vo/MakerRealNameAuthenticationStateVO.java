package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.VerifyStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "创客所有实名认证VO")
public class MakerRealNameAuthenticationStateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "身份证实名认证状态")
    private VerifyStatus idcardVerifyStatus;

    @ApiModelProperty(value = "活体验证状态")
    private VerifyStatus faceVerifyStatus;

    @ApiModelProperty(value = "银行卡验证状态")
    private VerifyStatus bankCardVerifyStatus;

    @ApiModelProperty(value = "手机号码验证状态")
    private VerifyStatus phoneNumberVerifyStatus;

}
