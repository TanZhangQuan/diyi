package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("diyi_pay_maker")
public class PayMakerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long id;
    /**
     * 支付清单ID
     */
    private Long payListId;

    /**
     * 创客ID
     */
        private Long makerId;

    /**
     * 创客身份，自然人，个体户，个独。
     */
        private String makerType;

    /**
     * 个体户/个独名称
     */
        private String individualBusinessName;

    /**
     * 总费用 外包费总额+身份验证费+支付手续费
     */
        private BigDecimal totalFee;

    /**
     * 外包费总额
     */
        private BigDecimal makerNeIncome;

    /**
     * 服务税费率
     */
        private BigDecimal serviceRate;

    /**
     * 创客税费:外包费总额*服务税费率
     */
        private BigDecimal makerTaxAndFee;

    /**
     * 创客到手:外包费总额-创客税费
     */
        private BigDecimal makerNetIncome;

    /**
     * 身份验证费
     */
        private BigDecimal auditFee;

    /**
     * 支付手续费
     */
        private BigDecimal payFee;

    /**
     * 1：待支付；2:企业已申请支付；3：企业已支付；4：平台已支付；5：已确认收款
     */
        private Integer payState;

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
        private Integer makerTaxState;

    /**
     * 发票开票状态:1:已开；0：未开
     */
        private Integer makerInvoiceState;

    /**
     * 发票类别:1,汇总代开；2，门征单开
     */
        private Integer invoiceType;

    /**
     * 支付说明
     */
        private String payMemo;

    /**
     * 创客发票类目:默认取订单中的默认信息，可更改，根据具体业务开，如*现代服务*市场推广费
     */
        private String makerInvoiceCategory;
    }