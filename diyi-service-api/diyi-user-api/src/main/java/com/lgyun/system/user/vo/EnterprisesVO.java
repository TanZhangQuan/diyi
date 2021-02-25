package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class EnterprisesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "关联创客数")
    private Integer makerNum;

}
