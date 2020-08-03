package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.OrderBusinessPattern;
import com.lgyun.common.enumeration.OrderPattern;
import com.lgyun.common.enumeration.OrderPayType;
import com.lgyun.common.enumeration.OrderState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_order")
public class OrderEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 运营公司ID
     */
    private Long runCompanyId;

    /**
     * 订单主题
     */
    private String orderTitle;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 工单数量
     */
    private Integer worksheetNum;

    /**
     * 发单模式：公开抢单，不公开抢单
     */
    private OrderPattern orderPattern;

    /**
     * 业务模式：个体户-总包+分包，自然人-总包+分包，个体户-众包，自然人-众包
     */
    private OrderBusinessPattern orderBusinessPattern;

    /**
     * 支付方式：总包+分包标准支付，总包+分包自助支付，众包企业自行支付，众包平台代收代付，总包+分包通联支付，众包通联支付
     */
    private OrderPayType orderPayType;

    /**
     * 综合税费率
     */
    private BigDecimal totalTaxRate;

    /**
     * 订单状态：抢单中，已关单，支付中，已支付
     */
    private OrderState orderState;

    /**
     * 订单说明
     */
    private String orderMemo;

    /**
     * 管理备注
     */
    private String manangeMemo;

    /**
     * 众包合同编号
     */
    private String massAgreementNo;

    /**
     * 服务订单编号
     */
    private String orderAgreementNo;

    /**
     * 平台发票类目
     */
    private String platformVoiceCategory;

    /**
     * 创客发票默认类目
     */
    private String makerVoiceCategory;

}
