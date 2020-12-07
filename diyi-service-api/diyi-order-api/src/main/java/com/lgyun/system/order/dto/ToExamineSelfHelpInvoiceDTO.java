package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq
 * @date 2020/9/21.
 * @time 14:26.
 */
@Data
public class ToExamineSelfHelpInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票主表ID
     */
    @NotNull(message = "请输入自助开票主表id")
    private Long selfHelpInvoiceId;

    /**
     * 总价税合计额
     */
    @NotNull(message = "请输入总价税合计额")
    private BigDecimal totlChargeMoneyNum;

    /**
     * 服务税费率
     */
    @NotNull(message = "请输入服务税费率")
    private BigDecimal serviceRate;

    /**
     * 总税费
     */
    @NotNull(message = "请输入总税费")
    private BigDecimal serviceTax;


    /**
     * 总服务税费
     */
    @NotNull(message = "请输入总服务税费")
    private BigDecimal serviceandTaxMoney;

    /**
     * 总开票手续费
     */
    @NotNull(message = "请输入总开票手续费")
    private BigDecimal serviceInvoiceFee;

    /**
     * 总需支付服务商税费
     */
    @NotNull(message = "请输入总需支付服务商税费")
    private BigDecimal totalPayProviderFee;

    /**
     * 总身份验证费
     */
    @NotNull(message = "请输入总身份验证费")
    private BigDecimal idendityConfirmFee;

    /**
     * 总服务外包费
     */
    @NotNull(message = "请输入总服务外包费")
    private BigDecimal serviceFee;

    /**
     * 账户名称
     */
    @NotBlank(message = "请输入账户名称")
    private String accountName;

    /**
     * 银行账号
     */
    @NotBlank(message = "请输入银行账号")
    private String accountNo;

    /**
     * 开户银行
     */
    @NotBlank(message = "请输入开户银行")
    private String accountBank;


}
