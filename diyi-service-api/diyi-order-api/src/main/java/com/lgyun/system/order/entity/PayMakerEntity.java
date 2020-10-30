package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.PayMakerPayState;
import com.lgyun.common.enumeration.MakerType;
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
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_pay_maker")
public class PayMakerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    private Long payEnterpriseId;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 创客身份: 自然人，个体户，个独
     */
    private MakerType makerType;

    /**
     * 个体户/个独ID
     */
    private Long individualId;

    /**
     * 创客到手=服务外包费-创客税费=服务外包费*服务税费率
     */
    private BigDecimal makerNetIncome;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 服务税费=服务外包费*服务税费率
     */
    private BigDecimal makerTaxFee;

    /**
     * 服务外包费=创客到手/(1-服务税费率)
     */
    private BigDecimal makerNeIncome;

    /**
     * 企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
     */
    private BigDecimal enterpriseBusinessAnnualFee;

    /**
     * 身份验证费
     */
    private BigDecimal auditFee;

    /**
     * 第三方支付手续费
     */
    private BigDecimal payFee;

    /**
     * 企业总支付额价税合计=服务外包费+身份验证费/个体户年费+第三方支付手续费
     */
    private BigDecimal totalFee;

    /**
     * 支付状态
     */
    private PayMakerPayState payState = PayMakerPayState.ENTERPRISEPAID;

    /**
     * 企业申请支付日期时间
     */
    private Date companyApplyDatetime;

    /**
     * 企业支付确认日期时间
     */
    private Date companyPayOkDatetime;

    /**
     * 平台支付确认日期时间
     */
    private Date platformPayOkDatetime;

    /**
     * 取交付支付确认函的确认到款日期时间
     */
    private Date makerConfirmDatetime;

    /**
     * 完税证明开票状态:1:已开；0：未开
     */
    private InvoiceState makerTaxState = InvoiceState.UNOPEN;

    /**
     * 发票开票状态:1:已开；0：未开
     */
    private InvoiceState makerInvoiceState = InvoiceState.UNOPEN;

    /**
     * 支付说明
     */
    private String payMemo;

    /**
     * 创客发票类目:默认取订单中的默认信息，可更改，根据具体业务开，如*现代服务*市场推广费
     */
    private String makerInvoiceCategory;

}
