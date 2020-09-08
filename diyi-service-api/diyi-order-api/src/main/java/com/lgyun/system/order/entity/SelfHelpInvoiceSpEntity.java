package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AddressType;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 自助开票-服务商：记录自助开票主表的提交给不同服务商的 Entity
 *
 * @author liangfeihu
 * @since 2020-08-19 16:10:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice_sp")
public class SelfHelpInvoiceSpEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票Id
     */
    private Long selfHelpInvoiceId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 提交人员
     */
    private String operatePerson;

    /**
     * 1，已分配服务商；2，已提交开票中；3，已撤回；4，已开票结束 这个是管理端运营老师负责，提交和撤回。
     */
    private SelfHelpInvoiceSpApplyState applyState;

    /**
     * 申请说明
     */
    private String applyDesc;

    /**
     * 结果说明
     */
    private String auditDesc;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 总开票金额合计
     */
    private BigDecimal valueMoneyNum;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 总服务税费
     */
    private BigDecimal serviceAndTaxMoney;

    /**
     * 总开票手续费
     */
    private BigDecimal serviceInvoiceFee;

    /**
     * 总身份验证费
     */
    private BigDecimal idendityConfirmFee;

    /**
     * 支付总额
     */
    private BigDecimal payTotalNum;

    /**
     * 收件地址Id
     */
    private Long addressId;

    /**
     * 收件地址性质：快递给管理中心；直接快递给客户
     */
    private AddressType addressType;
}
