package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class TotalInvoiceListEnterVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录服务商开具给商户的总包发票关联的支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long platformInvoicePayListId;

    @ApiModelProperty(value = "总包发票信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoicePrintId;

    @ApiModelProperty(value = "总包开票申请关联的支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceApplicationPayListId;

    @ApiModelProperty(value = "开票申请ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "总包开票状态")
    private CompanyInvoiceState companyInvoiceState;

    @ApiModelProperty(value = "申请状态")
    private String applicationState;

    @ApiModelProperty(value = "XXXXX")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "XXXXX")
    private String applyState;

    @ApiModelProperty(value = "XXXXX")
    private BigDecimal payToPlatformAmount;
}
