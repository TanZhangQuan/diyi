package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@NoArgsConstructor
@TableName("diyi_self_help_invoice_fee")
public class SelfHelpInvoiceFeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long handPayId;

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
    private String payType;

    /**
     * 自助开票收款账号ID
     */
    private Long handPayAccountId;

}
