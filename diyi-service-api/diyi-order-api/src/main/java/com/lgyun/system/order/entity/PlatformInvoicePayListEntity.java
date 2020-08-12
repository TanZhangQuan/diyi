package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.NoArgsConstructor;


/**
 * 记录服务商开具给商户的总包发票关联的支付清单 Entity
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_platform_invoice_pay_list")
public class PlatformInvoicePayListEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 开票ID
     */
    private Long invoicePrintId;

    /**
     * 支付清单ID
     */
    private Long payEnterpriseId;
}
