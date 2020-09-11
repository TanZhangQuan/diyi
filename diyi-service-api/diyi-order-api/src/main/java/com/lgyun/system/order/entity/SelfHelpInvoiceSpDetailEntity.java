package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 服务商开票明细：是从自助开票明细中选择过来的，信息是一致的 Entity
 *
 * @author liangfeihu
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
     * 服务商自助开票明细Id
     */
    private Long selfHelpInvoiceApplyProviderId;

    /**
     * 自助开票明细Id
     */
    private Long selfHelpInvoiceDetailId;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = PATTERN_DATETIME)
    @JsonFormat(pattern = PATTERN_DATETIME, timezone = "GMT+8")
    private Date MatchDatetime;

    /**
     * 发票扫描件（多张）
     */
    private String invoiceScanPictures;

    /**
     * 税票扫描件（多张）
     */
    private String taxScanPictures;

    /**
     * 发票处理人员
     */
    private String invoiceOperatePerson;

}
