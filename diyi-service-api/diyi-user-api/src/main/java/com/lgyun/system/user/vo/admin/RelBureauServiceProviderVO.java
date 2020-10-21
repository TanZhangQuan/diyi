package com.lgyun.system.user.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "可以匹配的服务商信息")
public class RelBureauServiceProviderVO {

    @ApiModelProperty("服务商ID")
    private Long serviceProviderId;

    @ApiModelProperty("服务商名称")
    private String serviceProviderName;
}
