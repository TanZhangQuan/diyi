package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "分包-门征VO")
public class EnterpriseSubcontractPortalVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "创客支付ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    @ApiModelProperty(value = "门征发票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerInvoiceId;

    @ApiModelProperty(value = "XXXXX")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerTaxRecordId;

    @ApiModelProperty(value = "发票编号")
    private String voiceSerialNo;

    @ApiModelProperty(value = "发票地址")
    private String makerVoiceUrl;

    @ApiModelProperty(value = "完税证明")
    private String makerTaxUrl;

    @ApiModelProperty(value = "开票状态")
    private CompanyInvoiceState companyInvoiceState;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "开票时间")
    private String invoicePrintDate;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "总包支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "工单号")
    private String worksheetId;

    @ApiModelProperty(value = "交付支付验收单")
    private String acceptPaysheetUrl;

    @ApiModelProperty(value = "交付支付验收单Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String acceptPaysheetId;

    @ApiModelProperty(value = "支付回单")
    private String enterprisePayReceiptUrl;
}
