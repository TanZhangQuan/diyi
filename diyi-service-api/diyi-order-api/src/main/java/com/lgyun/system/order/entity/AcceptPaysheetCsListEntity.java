package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 总包交付支付验收单清单表 Entity
 *
 * @author tzq
 * @since 2020-10-29 19:55:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_accept_paysheet_cs_list")
public class AcceptPaysheetCsListEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 总包交付支付验收单ID
     */
    private Long acceptPaysheetCsId;

    /**
     * 自主开票明细ID
     */
    private String selfHelpInvoiceDetailId;

}
