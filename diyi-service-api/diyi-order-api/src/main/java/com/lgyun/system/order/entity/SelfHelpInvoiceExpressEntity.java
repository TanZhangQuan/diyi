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
     * 自助开票服务商ID
     */
    private Long selfHelpInvoiceApplyProviderId;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递公司
     */
    private String expressCompanyName;

    /**
     * 快递回单或二维码
     */
    private String expressFileUrl;

    /**
     * 处理人员
     */
    private String operatePerson;

    /**
     * 快递更新人员电话
     */
    private String expressUpdatePersonTel;

    /**
     * 特殊说明
     */
    private String specialDesc;

}
