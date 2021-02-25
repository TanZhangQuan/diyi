package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoicePrintState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceCrowdSourcingVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "自助开票申请ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyId;

    @ApiModelProperty(value = "自助开票服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyProviderId;

    @ApiModelProperty(value = "服务商自助开票明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long providerSelfHelpInvoiceId;

    @ApiModelProperty(value = "自助开票详情ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceDetailId;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "开票清单")
    private String listFile;

    @ApiModelProperty(value = "发票(多张)")
    private String invoiceScanPictures;

    @ApiModelProperty(value = "完税证明发票(多张)")
    private String taxScanPictures;

    @ApiModelProperty(value = "开票日期")
    private String updateDatetime;

    @ApiModelProperty(value = "开票状态")
    private InvoicePrintState invoicePrintState;

    @ApiModelProperty(value = "快递单号")
    private String expressSheetNo;

    @ApiModelProperty(value = "快递公司")
    private String expressCompanyName;
}
