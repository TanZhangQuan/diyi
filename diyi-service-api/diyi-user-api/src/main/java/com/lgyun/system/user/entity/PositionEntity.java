package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Data
@NoArgsConstructor
@TableName("diyi_position")
public class PositionEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 外包岗位名称
     */
    private PositionName positionName;

    /**
     * 外包岗位描述
     */
    private String positionDesc;

}
