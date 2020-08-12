package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ApplicationState;
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
 * 开票申请：记录商户的总包开票申请 Entity
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_invoice_application")
public class InvoiceApplicationEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 开票类目ID
     */
    private Long invoiceCatalogId;

    /**
     * 申请日期
     */
    private Date applicationDate;

    /**
     * 申请人
     */
    private String applicationPerson;

    /**
     * 开票总额
     */
    private BigDecimal voiceTotalAmount;

    /**
     * 申请说明
     */
    private String applicationDesc;

    /**
     * 处理状态 1,申请中；2，已拒绝；3，已全额开具；4，已部分开具,5,已取消
     */
    private ApplicationState applicationState = ApplicationState.APPLYING;

    /**
     * 处理说明
     */
    private String applicationHandleDesc;
}
