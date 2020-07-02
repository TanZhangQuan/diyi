package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.OrderBusinessPattern;
import com.lgyun.common.enumeration.OrderPattern;
import com.lgyun.common.enumeration.OrderPayType;
import com.lgyun.common.enumeration.OrderState;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
@Data
@NoArgsConstructor
@TableName("diyi_order")
public class OrderEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @ApiModelProperty(value = "企业ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "运营公司ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long run_company_id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单主题")
    private String orderTitle;

    /**
     * 工单数量
     */
    @ApiModelProperty(value = "工单数量")
    private Integer worksheetNum;

    /**
     * 发单模式：公开抢单，不公开抢单
     */
    @ApiModelProperty(value = "发单模式")
    private OrderPattern orderPattern;

    /**
     * 业务模式：个体户-总包+分包，自然人-总包+分包，个体户-众包，自然人-众包
     */
    @ApiModelProperty(value = "发单模式")
    private OrderBusinessPattern businessPattern;

    /**
     * 支付方式：总包+分包标准支付，总包+分包自助支付，众包企业自行支付，众包平台代收代付，总包+分包通联支付，众包通联支付
     */
    @ApiModelProperty(value = "支付方式")
    private OrderPayType payType;

    /**
     * 综合税费率
     */
    @ApiModelProperty(value = "综合税费率")
    private BigDecimal totalTaxRate;

    /**
     * 订单状态：抢单中，已关单，支付中，已支付
     */
    @ApiModelProperty(value = "订单状态")
    private OrderState orderState;

    /**
     * 订单说明
     */
    @ApiModelProperty(value = "订单说明")
    private String orderMemo;

    /**
     * 管理备注
     */
    @ApiModelProperty(value = "管理备注")
    private String manangeMemo;

    /**
     * 众包合同编号
     */
    @ApiModelProperty(value = "众包合同编号")
    private String massAgreementNo;

    /**
     * 服务订单编号
     */
    @ApiModelProperty(value = "服务订单编号")
    private String orderAgreementNo;

    /**
     * 平台发票类目
     */
    @ApiModelProperty(value = "平台发票类目")
    private String platformVoiceCategory;

    /**
     * 创客发票默认类目
     */
    @ApiModelProperty(value = "创客发票默认类目")
    private String makerVoiceCategory;
}
