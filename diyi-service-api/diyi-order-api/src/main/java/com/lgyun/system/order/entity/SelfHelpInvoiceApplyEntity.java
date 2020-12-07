package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自助开票申请：记录自助开票主表的申请记录情况 Entity
 *
 * @author tzq
 * @since 2020-08-08 10:36:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice_apply")
public class SelfHelpInvoiceApplyEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票主表ID
     */
    private Long selfHelpInvoiceId;

    /**
     * 申请状态
     */
    private SelfHelpInvoiceApplyState applyState;

    /**
     * 申请说明
     */
    private String applyDesc;

    /**
     * 审核说明
     */
    private String auditDesc;

}
