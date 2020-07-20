package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import com.lgyun.core.mp.base.BaseEntity;
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
@NoArgsConstructor
@TableName("diyi_pay_enterprise")
public class PayEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    /**
     * 唯一性控制
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long id;
    
    /**
     * 商户ID
     */
        private Long enterpriseId;
    
    /**
     * 服务商ID
     */
        private Long serviceProviderId;
    
    /**
     * 支付清单URL
     */
        private String chargeListUrl;
    
    /**
     * 工单ID 可能关联，可选
     */
        private Long worksheetId;
    
    /**
     * 支付总额，外包费总额+总身份验证费+总开票手续费
     */
        private BigDecimal payToPlatformAmount;
    
    /**
     * 外包费总额
     */
        private BigDecimal sourcingAmount;
    
    /**
     * 服务税费率
     */
        private BigDecimal serviceRate;
    
    /**
     * 总税率：外包费总额*服务税费率
     */
        private BigDecimal totalTaxAndFee;
    
    /**
     * 创客数
     */
        private Integer makerNum;
    
    /**
     * 总身份验证费
     */
        private BigDecimal identifyFee;
    
    /**
     * 总支付手续费
     */
        private BigDecimal serviceFee;
    
    /**
     * 支付说明
     */
        private String payMemo;
    
    /**
     * 1：待支付；2：已支付；3：已确认收款
     */
        private Integer payState;
    
    /**
     * 支付确认日期时间
     */
        private Date payConfirmDatetime;
    
    /**
     * 确认回款日期时间
     */
        private Date confirmDatetime;
    
    /**
     * 确认到款人员ID
     */
        private Long employeeId;
    
    /**
     * 开票状态:1:已开；0：未开
     */
        private Integer companyInvoiceState;
    
    /**
     * 开票日期
     */
        private Date invoicePrintDate;
    
    /**
     * 开票要求说明
     */
        private String invoiceDemondDesc;
    }
