package com.lgyun.system.order.dto.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/9/21.
 * @time 14:26.
 */
@Data
public class ToExamineSelfHelpInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票主表id
     */
    @NotNull(message = "请输入自助开票主表id")
    private Long selfHelpInvoiceId;

    /**
     * 总价税合计额
     */
    @NotBlank(message = "请输入总价税合计额")
    private String totlChargeMoneyNum;

    /**
     * 服务税费率
     */
    @NotBlank(message = "请输入服务税费率")
    private String serviceRate;

    /**
     * 总税费
     */
    @NotBlank(message = "请输入总税费")
    private String serviceTax;


    /**
     * 总服务税费
     */
    @NotBlank(message = "请输入总服务税费")
    private String serviceandTaxMoney;

    /**
     * 总开票手续费
     */
    @NotBlank(message = "请输入总开票手续费")
    private String serviceInvoiceFee;

    /**
     * 总需支付服务商税费
     */
    @NotBlank(message = "请输入总需支付服务商税费")
    private String totalPayProviderFee;

    /**
     * 总身份验证费
     */
    @NotBlank(message = "请输入总身份验证费")
    private String idendityConfirmFee;

    /**
     * 总服务外包费
     */
    @NotBlank(message = "请输入总服务外包费")
    private String serviceFee;

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
