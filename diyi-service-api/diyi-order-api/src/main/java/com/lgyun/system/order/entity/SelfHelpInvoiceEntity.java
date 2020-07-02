package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Data
@NoArgsConstructor
@TableName("diyi_self_help_invoice")
public class SelfHelpInvoiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 唯一性控制
     */
        @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long selfHelpInvoiceId;

    /**
     * 运营公司ID
     */
        private Long companyId;

    /**
     * 创客支付ID
     */
        private Long payId;

    /**
     * 开票人
     */
        private Long invoicePeopleId;

    /**
     * 收件地址Id
     */
        private Long addressId;

    /**
     * 开票类目
     */
        private String invoiceType;

    /**
     * 价税合计额
     */
        private BigDecimal chargeMoneyNum;

    /**
     * 流水合同URL
     */
        private String flowContractUrl;

    /**
     * 业务合同URL
     */
        private String businessContractUrl;

    /**
     * 账户余额url
     */
        private String accountBalanceUrl;
    }
