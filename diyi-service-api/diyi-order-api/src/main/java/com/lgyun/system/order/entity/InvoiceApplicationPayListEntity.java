package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 总包开票申请关联的支付清单 Entity
 *
 * @author tzq
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
