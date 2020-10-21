package com.lgyun.system.order.vo.admin;

import com.lgyun.common.enumeration.BureauServiceProviderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "相关局查询匹配的服务商基本信息及交易金额")
public class TransactionByBureauServiceProviderInfoVO {

    @ApiModelProperty("服务商编号")
    private Long serviceProviderId;

    @ApiModelProperty("服务商名称")
    private String serviceProviderName;

    @ApiModelProperty("服务商交易金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("服务商状态")
    private BureauServiceProviderStatus bureauServiceProviderStatus;

    @ApiModelProperty("服务商匹配时间")
    private LocalDateTime createTime;
}
