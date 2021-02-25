package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "服务商查询总包发票VO")
public class InvoiceServiceLumpVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包申请和支付清单表ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceApplicationPayListId;

    @ApiModelProperty(value = "总包申请ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceApplicationId;

    @ApiModelProperty(value = "支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "总包申请状态")
    private String applicationState;

    @ApiModelProperty(value = "总包开票状态")
    private CompanyInvoiceState companyInvoiceState;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "开票说明")
    private String applicationDesc;

    @ApiModelProperty(value = "申请状态")
    private String applyState;

    @ApiModelProperty(value = "总包发票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoicePrintId;
}
