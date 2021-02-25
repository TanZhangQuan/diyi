package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "服务商查询总包发票详情VO")
public class InvoiceServiceDetailSummaryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "价税合计额")
    private String payToPlatformAmount;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "纳税号码")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票资料-地址和电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票资料-开户银行和账号")
    private String invoiceBankNameAccount;

    @ApiModelProperty(value = "服务商名字")
    private String serviceProviderName;

    @ApiModelProperty(value = "支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "工单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String worksheetId;

    @ApiModelProperty(value = "分包开票状态")
    private InvoiceState subcontractingInvoiceState;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "发票")
    private String companyInvoiceUrl;

    @ApiModelProperty(value = "支付回单")
    private String enterprisePayReceiptUrl;

    @ApiModelProperty(value = "税票")
    private String makerTaxUrl;

    @ApiModelProperty(value = "创客发票开票类别")
    private MakerInvoiceType makerInvoiceType;

}
