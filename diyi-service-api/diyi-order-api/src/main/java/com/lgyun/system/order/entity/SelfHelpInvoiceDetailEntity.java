package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.InvoicePrintState;
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
     * 开票人身份类别 1，自然人；2，个体户；3，个独；4，合伙企业；5，有限公司
     */
    private InvoicePeopleType invoicePeopleType;

    /**
     * 开票状态 1，开票中；2，已开票；3，开票失败
     */
    private InvoicePrintState invoicePrintState = InvoicePrintState.INVOICEING;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 非创客开票人ID
     */
    private Long noneMakerInvoicePersonId;

    /**
     * 各类企业ID
     */
    private Long allKindEnterpriseID;

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
     * 交付支付验收单URL
     */
    private String deliverSheetUrl;

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

}
