package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.WorkAchievementState;
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
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_work_achievement")
public class WorkAchievementEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 工单id
     */
    private Long worksheetId;

    /**
     * 工作成果说明
     */
    private String workExplain;

    /**
     * 工作结果url
     */
    private String workUrl;

    /**
     * 验收金额
     */
    private BigDecimal checkMoneyNum;

    /**
     * 验收说明
     */
    private String checkDesc;

    /**
     * 验收人员
     */
    private String checkPerson;

    /**
     * 验收日期时间
     */
    private Date checeDatetime;

    /**
     * 工作成果状态
     */
    private WorkAchievementState workAchievementState;

}
