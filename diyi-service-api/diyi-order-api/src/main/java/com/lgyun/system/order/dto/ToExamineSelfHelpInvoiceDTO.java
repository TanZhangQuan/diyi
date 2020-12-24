package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.AuditState;
import com.lgyun.common.enumeration.MakerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private BigDecimal totlChargeMoneyNum;

    @ApiModelProperty(value = "服务税费率")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "总税费")
    private BigDecimal serviceTax;

    @ApiModelProperty(value = "总服务税费")
    private BigDecimal serviceandTaxMoney;

    @ApiModelProperty(value = "总开票手续费")
    private BigDecimal serviceInvoiceFee;

    @ApiModelProperty(value = "总需支付服务商税费")
    private BigDecimal totalPayProviderFee;

    @ApiModelProperty(value = "总身份验证费")
    private BigDecimal idendityConfirmFee;

    @ApiModelProperty(value = "总服务外包费")
    private BigDecimal serviceFee;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "银行账号")
    private String accountNo;

    @ApiModelProperty(value = "开户银行")
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
