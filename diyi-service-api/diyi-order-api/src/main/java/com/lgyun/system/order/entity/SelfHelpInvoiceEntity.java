package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@NoArgsConstructor
@TableName("diyi_self_help_invoice")
public class SelfHelpInvoiceEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    /**
     * 运营公司ID
     */
    private Long companyId;

    /**
     * 创客支付ID
     */
    private Long payId;

    /**
     * 申请创客ID
     */
    private Long applyMakerId;

    /**
     * 申请商户ID
     */
    private Long applyEnterpriseId;

    /**
     * 申请渠道ID
     */
    private Long applyChannelId;

    /**
     * 申请合伙人ID
     */
    private Long applyPartnerId;

    /**
     * 平台用户ID
     */
    private Long applyManagementId;

    /**
     * 原自助开票ID
     */
    private Long originalSelfHelpId;

    /**
     * 开票人身份类别 1，自然人；2，个体户；3，个独
     */
    private String invoicePeopleType;

    /**
     * 开票清单文件
     */
    private String listFile;

    /**
     * 总价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 总服务税费
     */
    private BigDecimal serviceAndTaxMoney;

    /**
     * 总服务费
     */
    private BigDecimal serviceFee;

    /**
     * 总税
     */
    private BigDecimal serviceTax;

    /**
     * 总开票手续费
     */
    private BigDecimal serviceInvoiceFee;

    /**
     * 总身份验证费
     */
    private BigDecimal idendityConfirmFee;

    /**
     * 收件地址Id
     */
    private Long addressId;

    /**
     * 1.未审核，2审核通过，3不通过
     */
    private InvoiceState invoiceState;

}
