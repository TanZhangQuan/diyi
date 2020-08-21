package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AuditState;
import com.lgyun.common.enumeration.EnterprisePayState;
import com.lgyun.common.enumeration.InvoiceState;
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
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_pay_enterprise")
public class PayEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 支付清单URL
     */
    private String chargeListUrl;

    /**
     * 工单ID
     */
    private Long worksheetId;

    /**
     * 支付总额=外包费总额+总身份验证费+总开票手续费
     */
    private BigDecimal payToPlatformAmount;

    /**
     * 外包费总额
     */
    private BigDecimal sourcingAmount;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 总税费=外包费总额*服务税费率
     */
    private BigDecimal totalTaxFee;

    /**
     * 创客数
     */
    private Integer makerNum;

    /**
     * 总身份验证费
     */
    private BigDecimal identifyFee;

    /**
     * 总支付手续费
     */
    private BigDecimal serviceFee;

    /**
     * 支付说明
     */
    private String payMemo;

    /**
     * 审核状态
     */
    private AuditState auditState = AuditState.EDITING;

    /**
     * 支付给平台状态：待支付，已支付，已确认收款
     */
    private EnterprisePayState payState = EnterprisePayState.TOPAY;

    /**
     * 支付确认日期时间
     */
    private Date payConfirmDateTime;

    /**
     * 确认回款日期时间
     */
    private Date confirmDateTime;

    /**
     * 确认到款人员ID
     */
    private Long employeeId;

    /**
     * 开票状态：未开，已开
     */
    private InvoiceState companyInvoiceState = InvoiceState.UNOPEN;

    /**
     * 开票日期
     */
    private Date invoicePrintDate;

}
