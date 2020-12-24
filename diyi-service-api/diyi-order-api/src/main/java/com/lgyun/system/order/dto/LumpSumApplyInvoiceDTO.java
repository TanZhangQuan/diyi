package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.InvoiceMode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class LumpSumApplyInvoiceDTO implements Serializable {

    @ApiModelProperty(value = "服务商吗名称")
    @NotBlank(message = "请输入服务商吗名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "总包申请ID")
    @NotNull(message = "请选择总包申请")
    private Long applicationId;

    @ApiModelProperty(value = "发票")
    @NotBlank(message = "请上传发票")
    private String companyInvoiceUrl;

    @ApiModelProperty(value = "快递单号")
    @NotBlank(message = "请输入快递单号")
    private String expressSheetNo;

    @ApiModelProperty(value = "快递公司")
    @NotBlank(message = "请输入快递公司")
    private String expressCompanyName;

    @ApiModelProperty(value = "说明")
    private String invoiceDesc;

    @ApiModelProperty(value = "服务商")
    private Long serviceProviderId;

    @ApiModelProperty(value = "货物或应税劳务、服务名称,发票类")
    @NotBlank(message = "请输入发票分类")
    private String invoiceCategory;

    @ApiModelProperty(value = "开票方式", notes = "com.lgyun.common.enumeration.InvoiceMode")
    @NotNull(message = "请选择开票方式")
    private InvoiceMode invoiceMode;

    @ApiModelProperty(value = "部分开票金额")
    private BigDecimal partInvoiceAmount;
}
