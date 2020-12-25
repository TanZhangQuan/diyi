package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "")
public class AgreementMakerEnterAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "协议编号")
    private String agreementNo;

    @ApiModelProperty(value = "创客名称")
    private String makerName;

    @ApiModelProperty(value = "商户名字")
    private String enterpriseName;

    @ApiModelProperty(value = "纸质协议")
    private String agreementUrl;

    @ApiModelProperty(value = "发布时间")
    private String createTime;
}
