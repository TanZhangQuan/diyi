package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.GetType;
import com.lgyun.common.enumeration.WorksheetMakerState;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_worksheet_maker")
public class WorksheetMakerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 工单ID
     */
    private Long worksheetId;

    /**
     * 获得方式：1,抢单获得；2，派单获得
     */
    private GetType getType;

    /**
     * 工单创客的状态：1待提交，2待验证，3验证通过，4验证失败
     */
    private WorksheetMakerState worksheetMakerState = WorksheetMakerState.SUBMITTED;

    /**
     * 工作成果说明
     */
    private String achievementDesc;

    /**
     * 工作成果附件
     */
    private String achievementFiles;

    /**
     * 提交工作成果日期
     */
    private Date achievementDate;

    /**
     * 验收金额
     */
    private BigDecimal checkMoney;

    /**
     * 验收时间
     */
    private Date checkDate;

}
