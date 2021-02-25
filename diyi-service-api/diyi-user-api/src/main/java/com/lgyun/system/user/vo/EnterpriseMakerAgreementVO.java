package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.SignType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@ApiModel(description = "XXXXXX")
public class EnterpriseMakerAgreementVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "商户")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "合同编号")
    private String agreementNo;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "签署类型")
    private SignType signType;

    @ApiModelProperty(value = "纸质合同")
    private String agreementUrl;

    @ApiModelProperty(value = "签署状态")
    private SignState signState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
}
