package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 总包发票信息：记录服务商开具给商户的总包发票，一次开票可能多个清单一起 Entity
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_platform_invoice")
public class PlatformInvoiceEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 总包开票申请ID
     */
    private Long applicationId;

    /**
     * 开票日期
     */
    private Date invoicePrintDate;

    /**
     * 开票总额
     */
    private BigDecimal invoiceTotalAmount;

    /**
     * 发票张数
     */
    private Integer invoiceNumbers;

    /**
     * 开票人
     */
    private String invoicePrintPerson;

    /**
     * 快递单号
     */
    private String expressSheetNo;

    /**
     * 快递公司
     */
    private String expressCompanyName;

    /**
     * 快递更新日期
     */
    private Date expressUpdateDatetime;

    /**
     * 快递更新人员
     */
    private Date expressUpdatePerson;

    /**
     * 快递更新人员电话
     */
    private String expressUpdatePersonTel;

    /**
     * 开票说明
     */
    private String invoiceDesc;
}
