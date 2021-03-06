package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 服务商开票明细：是从自助开票明细中选择过来的，信息是一致的 Entity
 *
 * @author tzq
 * @since 2020-08-19 16:10:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice_sp_detail")
public class SelfHelpInvoiceSpDetailEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票明细Id
     */
    private Long selfHelpInvoiceDetailId;

    /**
     * 发票扫描件（多张）
     */
    private String invoiceScanPictures;

    /**
     * 税票扫描件（多张）
     */
    private String taxScanPictures;

}
