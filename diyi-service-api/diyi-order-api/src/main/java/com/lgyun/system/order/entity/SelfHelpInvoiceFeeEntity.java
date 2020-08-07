package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.PaymentType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice_fee")
public class SelfHelpInvoiceFeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票Id
     */
    private Long selfHelpInvoiceId;

    /**
     * 提交日期
     */
    private Date putinDate;

    /**
     * 核价日期
     */
    private Date givePriceDate;

    /**
     * 总税费
     */
    private BigDecimal totalTaxFee;

    /**
     * 基础税费
     */
    private BigDecimal basicTaxFee;

    /**
     * 基础税费率
     */
    private BigDecimal basicTaxFeeRate;

    /**
     * 开票手续费
     */
    private BigDecimal invoiceFee;

    /**
     * 身份验证费
     */
    private BigDecimal identifyFee;

    /**
     * 支付说明
     */
    private String payDesc;

    /**
     * 支付回单
     */
    private String payCertificate;

    /**
     * 支付方式 1，微信；2，支付宝，3，银行转账；4，现金
     */
    private PaymentType payType;

    /**
     * 自助开票收款账号ID
     */
    private Long handPayAccountId;

}
