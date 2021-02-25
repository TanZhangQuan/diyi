package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class AgreementMakerWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客-商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerEnterpriseId;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "创客名称")
    private String name;

    @ApiModelProperty(value = "创客身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "创客银行卡号码")
    private String bankCardNo;

    @ApiModelProperty(value = "创客银行卡验证状态")
    private String bankCardVerifyStatus;

    @ApiModelProperty(value = "纸质合同")
    private String agreementUrl;

    @ApiModelProperty(value = "签署状态")
    private SignState signState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
