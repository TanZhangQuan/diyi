package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.WorkAchievementEntity;
import com.lgyun.system.order.vo.WorkAchievementVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
public class WorkAchievementWrapper extends BaseEntityWrapper<WorkAchievementEntity, WorkAchievementVO> {

    public static WorkAchievementWrapper build() {
        return new WorkAchievementWrapper();
    }

    @Override
    public WorkAchievementVO entityVO(WorkAchievementEntity workAchievement) {

        if (workAchievement == null){
            return null;
        }

        return BeanUtil.copy(workAchievement, WorkAchievementVO.class);
    }

}
