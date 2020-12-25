package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.CooperateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class CooperationEnterprisesListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "合作次数")
    private Integer cooperationNum;

    @ApiModelProperty(value = "合作金额")
    private BigDecimal cooperationMoney;

    @ApiModelProperty(value = "合作类型")
    private CooperateType cooperateType;

    @ApiModelProperty(value = "合作状态")
    private CooperateStatus cooperateStatus;

    @ApiModelProperty(value = "开始合作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
