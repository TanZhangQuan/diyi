package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.InvoiceDemand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "添加或编辑服务商发票类目DTO")
public class AddOrUpdateEnterpriseProviderInvoiceCatalogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户-服务商发票类目ID")
    private Long enterpriseProviderInvoiceCatalogId;

    @ApiModelProperty(value = "发票类目名称")
    @NotBlank(message = "请输入发票类目名称")
    private String invoiceCatalogName;

    @ApiModelProperty(value = "开票诉求", notes = "com.lgyun.common.enumeration.InvoiceDemand")
    @NotNull(message = "请选择开票诉求")
    private InvoiceDemand invoiceDemand;

    @ApiModelProperty(value = "开票诉求备注")
    private String invoiceDemandDesc;

}
