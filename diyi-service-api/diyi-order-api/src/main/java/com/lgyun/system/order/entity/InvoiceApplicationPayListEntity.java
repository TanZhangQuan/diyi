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

import java.util.Date;

/**
 * 总包开票申请关联的支付清单 Entity
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_invoice_application_pay_list")
public class InvoiceApplicationPayListEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 总包开票申请ID
     */
    private Long applicationId;

    /**
     * 支付清单ID
     */
    private Long payEnterpriseId;
}
