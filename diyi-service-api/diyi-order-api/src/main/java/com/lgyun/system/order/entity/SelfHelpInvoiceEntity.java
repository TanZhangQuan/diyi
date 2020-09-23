package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 自助开票主表 Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice")
public class SelfHelpInvoiceEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 在线支付ID
     */
    private Long onlinePayId;

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
     * 开票人身份类别 1，自然人；2，个体户；3，个独；4，合伙企业；5，有限公司
     */
    private InvoicePeopleType invoicePeopleType;

    /**
     * 开票清单文件
     */
    private String listFile;

    /**
     * 众包支付模式：标准支付；扩展支付；商户代付税费
     */
    private CrowdSourcingPayType payType;

    /**
     * 总需支付服务商税费=总服务税费+总开票手续费+总身份验证费，自动计算
     */
    private BigDecimal totalPayProviderFee;

    /**
     * 总服务税费=总服务外包费*服务税费率
     */
    private BigDecimal serviceAndTaxMoney;

    /**
     * 总服务外包费：合计，明细价税合计，依据明细自动计算
     */
    private BigDecimal serviceFee;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 总税费，一般不填
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
     * 当前状态：未申请，申请编辑中，审核中；已通过开票中；已驳回；已开票结束
     */
    private SelfHelpInvoiceApplyState currentState = SelfHelpInvoiceApplyState.NOTAPPLY;

    /**
     * 申请次数
     */
    private Integer applyNum;

    /**
     * 收件地址Id
     */
    private Long addressId;

    /**
     * 核价人员
     */
    private String confirmPricePerson;

    /**
     * 核价时间
     */
    private Date confirmPriceDatetime;

    /**
     * 扩展支付税费发票，多张间用“；”隔开。服务商开具
     */
    private String extendPayInvoices;

    /**
     * 总价税合计额
     */
    private BigDecimal totlChargeMoneyNum;

}
