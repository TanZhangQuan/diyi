package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ApplyState;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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
     * 主键
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

    /**
     * 自助开票Id
     */
        private Long selfHelpInvoiceId;

    /**
     * 服务商ID
     */
        private Long serviceProviderId;

    /**
     *  提交人员
     */
        private String operatePerson;

    /**
     * 申请日期 可以多次申请，每次独立数据
     */
        private Date applyDate;

    /**
     *  申请状态 1，已提交开票中；2，已撤回；3，已开票结束。。。这个是管理端运营老师负责，提交和撤回。
     */
        private ApplyState applyState;

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
     * 收件地址性质 1，快递给管理中心；2，直接快递给客户
     */
        private String addressType;
    }