package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "服务商更新快递信息DTO")
public class SelfHelpInvoiceExpressDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票ID")
    @NotNull(message = "请输入自助开票编号")
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "快递单号")
    @NotNull(message = "请输入运单号")
    private String expressSheetNo;

    @ApiModelProperty(value = "快递公司")
    @NotNull(message = "请选择快递公司")
    private String expressCompanyName;

}
