package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CooperateStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class RelationEnterpriseServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *服务商id
     */
    @ApiModelProperty(value = "XXXXX")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;
    /**
     *服务商名称
     */
    @ApiModelProperty(value = "XXXXX")
    private String serviceProviderName;

    /**
     * 合作状态
     */
    @ApiModelProperty(value = "XXXXX")
    private CooperateStatus cooperateStatus;
}
