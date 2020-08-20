package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 服务商自助开票明细Id
     */
    private Long selfHelpInvoiceApplyProviderId;

    /**
     * 自助开票明细Id
     */
    private Long selfHelpInvoiceDetailId;

    /**
     * 发票扫描件（可多张）
     */
    private String invoiceScanPictures;

    /**
     * 税票扫描件（可多张）
     */
    private String taxScanPictures;

    /**
     * 发票处理人员
     */
    private String invoiceOperatePerson;

    /**
     * 更新日期
     */
    private Date updateDatetime;
}
