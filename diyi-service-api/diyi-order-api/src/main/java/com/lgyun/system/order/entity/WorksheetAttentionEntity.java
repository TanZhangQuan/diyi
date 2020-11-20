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
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_worksheet_attention")
public class WorksheetAttentionEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 工单关注编号
     */
    private Long worksheetAttentionNo;

}
