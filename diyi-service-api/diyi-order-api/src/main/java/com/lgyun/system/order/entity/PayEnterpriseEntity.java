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
     * 企业总支付额价税合计总额=服务外包费总额+身份验证费总额/个体户年费总额+第三方支付手续费总额
     */
    private BigDecimal payToPlatformAmount;

    /**
     * 服务税费总额=服务外包费总额*服务税费率
     */
    private BigDecimal totalTaxFee;

    /**
     * 创客到手总额
     */
    private BigDecimal totalMakerNetIncome;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * '服务外包费总额'
     */
    private BigDecimal sourcingAmount;

    /**
     * 企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
     */
    private BigDecimal enterpriseBusinessAnnualFee;

    /**
     * 身份验证费总额
     */
    private BigDecimal identifyFee;

    /**
     * 第三方支付手续费总额
     */
    private BigDecimal serviceFee;

    /**
     * 创客数
     */
    private Integer makerNum;

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
