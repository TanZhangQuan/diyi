package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class PayEnterpriseDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "总包支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "总包支付回单(多张)")
    private String enterprisePayReceiptUrls;

    @ApiModelProperty(value = "工单编号")
    private String worksheetId;

    @ApiModelProperty(value = "总包+分包交付支付验收单")
    private String acceptPaysheetUrls;

    @ApiModelProperty(value = "审核状态")
    private PayEnterpriseAuditState auditState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创客发票开票类别")
    private MakerInvoiceType makerInvoiceType;

    @ApiModelProperty(value = "支付状态")
    private PayEnterprisePayState payState;

    @ApiModelProperty(value = "总包开票状态")
    private CompanyInvoiceState companyInvoiceState;

    @ApiModelProperty(value = "分包开票状态")
    private InvoiceState subcontractingInvoiceState;

    @ApiModelProperty(value = "企业总支付额价税合计总额")
    private BigDecimal payToPlatformAmount;

}
