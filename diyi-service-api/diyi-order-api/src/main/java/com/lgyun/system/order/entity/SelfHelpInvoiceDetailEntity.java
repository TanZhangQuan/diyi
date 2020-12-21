package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
@TableName("diyi_self_help_invoice_detail")
public class SelfHelpInvoiceDetailEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票Id
     */
    private Long selfHelpInvoiceId;

    /**
     * 开票人姓名
     */
    private String invoicePeopleName;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 开票人身份类别 1，自然人；2，个体户；3，个独；4，合伙企业；5，有限公司
     */
    private MakerType makerType;

    /**
     * 个体户/个独ID
     */
    private Long individualId;

    /**
     * 当前状态：编辑中，申请中，审核中；已审核待付费；已付费开票中；已驳回；已开票结束
     */
    private SelfHelpInvoiceApplyState invoicePrintState = SelfHelpInvoiceApplyState.NOTAPPLY;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 非创客开票人ID
     */
    private Long noneMakerInvoicePersonId;

    /**
     * 开票类目
     */
    private String invoiceType;

    /**
     * 增值税税率
     */
    private BigDecimal valueAddedTaxRate;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 支付回单URL
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

    /**
     * 需支付服务商税费=价税合计额*服务税费率+开票手续费+身份验证费
     */
    private BigDecimal payProviderFee;

}
