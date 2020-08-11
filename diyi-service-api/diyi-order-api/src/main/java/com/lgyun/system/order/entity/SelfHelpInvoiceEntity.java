package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ApplyState;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

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
     * 核价人员
     */
    private Long confirmPricePerson;

    /**
     * 核价时间
     */
    private Date ConfirmPriceDatetime;

    /**
     * 当前状态
     */
    private ApplyState currentState = ApplyState.EDITING;

    /**
     * 快递单号
     */
    private String expressSheetNo;

    /**
     * 快递公司名称
     */
    private String expressCompanyName;

    /**
     * 快递更新日期
     */
    private Date expressUpdateDatetime;

    /**
     * 快递更新人员
     */
    private String expressUpdatePerson;

    /**
     * 快递更新人员电话
     */
    private String expressUpdatePersonTel;

}
