package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice_express")
public class SelfHelpInvoiceExpressEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一性控制
     */
    private Long selfHelpInvoiceId;

    /**
     * 地址id
     */
    private Long addressId;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递回单或二维码
     */
    private String expressFileUrl;

}
