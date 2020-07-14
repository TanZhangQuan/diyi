package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("diyi_self_help_invoice_detail")
public class SelfHelpInvoiceDetailEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long selfHelpInvoiceDetailId;

    /**
     * 自助开票Id
     */
        private Long selfHelpInvoiceId;

    /**
     * 开票人姓名
     */
        private String invoicePeopleName;

    /**
     * 创客ID
     */
        private Long makerId;

    /**
     * 非创客开票人ID
     */
        private Long noneMakerInvoicePersonId;

    /**
     * 个体户或个独名称
     */
        private String bizName;

    /**
     * 统一社会信用代码
     */
        private String socialCreditNo;

    /**
     * 开票类目
     */
        private String invoiceType;

    /**
     * 价税合计额
     */
        private BigDecimal chargeMoneyNum;

    /**
     * 流水回单URL
     */
        private String flowContractUrl;

    /**
     * 业务合同URL
     */
        private String businessContractUrl;

    /**
     * 账户余额url
     */
        private String accountBalanceUrl;

    /**
     * 开票手续费
     */
        private BigDecimal serviceInvoiceFee;

    /**
     * 身份验证费
     */
        private BigDecimal idendityConfirmFee;


    public SelfHelpInvoiceDetailEntity(Long selfHelpInvoiceId, String invoicePeopleName, Long makerId, Long noneMakerInvoicePersonId, String bizName, String socialCreditNo, String invoiceType, BigDecimal chargeMoneyNum, String flowContractUrl, String businessContractUrl, String accountBalanceUrl, BigDecimal serviceInvoiceFee, BigDecimal idendityConfirmFee) {
        this.selfHelpInvoiceId = selfHelpInvoiceId;
        this.invoicePeopleName = invoicePeopleName;
        this.makerId = makerId;
        this.noneMakerInvoicePersonId = noneMakerInvoicePersonId;
        this.bizName = bizName;
        this.socialCreditNo = socialCreditNo;
        this.invoiceType = invoiceType;
        this.chargeMoneyNum = chargeMoneyNum;
        this.flowContractUrl = flowContractUrl;
        this.businessContractUrl = businessContractUrl;
        this.accountBalanceUrl = accountBalanceUrl;
        this.serviceInvoiceFee = serviceInvoiceFee;
        this.idendityConfirmFee = idendityConfirmFee;
    }
}
