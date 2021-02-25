package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.AuditState;
import com.lgyun.common.enumeration.MakerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class ToExamineSelfHelpInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票主表ID")
    @NotNull(message = "请输入自助开票主表id")
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "总价税合计额")
    @Min(value = 0, message = "总价税合计额不能小于0")
    private BigDecimal totlChargeMoneyNum;

    @ApiModelProperty(value = "服务税费率")
    @Min(value = 0, message = "服务税费率不能小于0")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "总税费")
    @Min(value = 0, message = "总税费不能小于0")
    private BigDecimal serviceTax;

    @ApiModelProperty(value = "总服务税费")
    @Min(value = 0, message = "总服务税费不能小于0")
    private BigDecimal serviceandTaxMoney;

    @ApiModelProperty(value = "总开票手续费")
    @Min(value = 0, message = "总开票手续费不能小于0")
    private BigDecimal serviceInvoiceFee;

    @ApiModelProperty(value = "总需支付服务商税费")
    @Min(value = 0, message = "总需支付服务商税费不能小于0")
    private BigDecimal totalPayProviderFee;

    @ApiModelProperty(value = "总身份验证费")
    @Min(value = 0, message = "总身份验证费不能小于0")
    private BigDecimal idendityConfirmFee;

    @ApiModelProperty(value = "总服务外包费")
    @Min(value = 0, message = "总服务外包费不能小于0")
    private BigDecimal serviceFee;

    @ApiModelProperty(value = "账户名称")
    @NotBlank(message = "账户名称不能为空")
    private String accountName;

    @ApiModelProperty(value = "银行账号")
    @NotBlank(message = "银行账号不能为空")
    private String accountNo;

    @ApiModelProperty(value = "开户银行")
    @NotBlank(message = "开户银行不能为空")
    private String accountBank;

    @ApiModelProperty(value = "驳回说明")
    private String auditDesc;

    @ApiModelProperty(value = "审核状态", notes = "com.lgyun.common.enumeration.AuditState")
    @NotNull(message = "请选择审核状态")
    private AuditState auditStatu;

    @ApiModelProperty(value = "开票人身份", notes = "com.lgyun.common.enumeration.MakerType")
    @NotNull(message = "请输入开票人身份")
    private MakerType makerType;

    @ApiModelProperty(value = "服务商ID,开票人为自然人才要")
    private Long serviceProviderId;

}
