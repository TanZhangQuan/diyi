package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.PayState;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
@Data
@NoArgsConstructor
@TableName("diyi_pay")
public class PayEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
    * 平台支付信息ID
    */
    @ApiModelProperty(value = "主键")
    @TableId(value = "pay_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payId;
    /**
    * 订单ID
    */
    @ApiModelProperty(value = "订单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;
    /**
    * 创客ID
    */
    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;
    /**
     * 创客类别：自然人，个体户
    */
    @ApiModelProperty(value = "创客类别")
    private MakerType makerType;
    /**
     * 个体户名称
     */
    private String individualBusinessName;
    /**
     * 创客到手外包费
     */
    private BigDecimal makerNeincome;
    /**
     * 创客税费
     */
    private BigDecimal makerTaxAndFee;
    /**
     * 企业管理服务费
     */
    private BigDecimal companyManageFee;
    /**
     * 身份验证费
     */
    private BigDecimal auditFee;
    /**
     * 支付手续费
     */
    private BigDecimal payFee;
    /**
     * 支付状态：待支付，企业已申请支付，企业已支付，平台已支付，已确认收款
     */
    private PayState payState;
    /**
     * 发票上传日期
     */
    private Date companyApplyDateTime;
    /**
     * 发票上传日期
     */
    private Date companyPayOkDateTime;
    /**
     * 发票上传日期
     */
    private Date platformPayOkDateTime;
    /**
     * 发票上传日期
     */
    private Date makerConfirmDateTime;
    /**
     * 完税证明开票状态: 已开，未开
     */
    private String makerTaxState;
    /**
     * 发票开票状态: 已开，未开
     */
    private String makerVoiceState;
    /**
     * 支付说明
     */
    private String payMemo;
    /**
     * 创客发票类目
     */
    private String makerVoiceCategory;
}
