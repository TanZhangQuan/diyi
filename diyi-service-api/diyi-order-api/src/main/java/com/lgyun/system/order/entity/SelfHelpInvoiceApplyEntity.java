package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ApplyState;
import com.lgyun.common.enumeration.AuditState;
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
 * 自助开票申请：记录自助开票主表的申请记录情况 Entity
 *
 * @author liangfeihu
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
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 自助开票主表id
     */
    private Long selfHelpInvoiceId;

    /**
     * 申请日期
     */
    private Date applyDate;

    /**
     * 申请状态
     */
    private ApplyState applyState;

    /**
     * 申请说明
     */
    private String applyDesc;

    /**
     * 审核说明
     */
    private String auditDesc;

}
