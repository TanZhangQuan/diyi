package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "分包 -汇总VO")
public class EnterpriseSubcontractInvoiceVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "汇总代开发票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerTotalInvoiceId;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    @ApiModelProperty(value = "发票编码")
    private String invoiceSerialNo;

    @ApiModelProperty(value = "总包发票")
    private String companyInvoiceUrl;

    @ApiModelProperty(value = "完税证明")
    private String makerTaxUrl;

    @ApiModelProperty(value = "开票状态")
    private InvoiceState subcontractingInvoiceState;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "申请日期")
    private String invoicePrintDate;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "总包支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "工单号")
    private String worksheetId;

    @ApiModelProperty(value = "开票日期")
    private String companyVoiceUploadDatetime;

    @ApiModelProperty(value = "交付支付验收单")
    private String acceptPaysheetUrl;

    @ApiModelProperty(value = "交付支付验收单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String acceptPaysheetId;

    @ApiModelProperty(value = "支付回单")
    private String enterprisePayReceiptUrl;

    @ApiModelProperty(value = "创客发票开票类别")
    private MakerInvoiceType makerInvoiceType;
}
