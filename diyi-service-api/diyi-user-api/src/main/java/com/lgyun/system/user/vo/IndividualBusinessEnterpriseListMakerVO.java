package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class IndividualBusinessEnterpriseListMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个体户/个独ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "个体户/个独名称")
    private String ibname;

    @ApiModelProperty(value = "注册时候选名称")
    private String candidatedNames;

}
