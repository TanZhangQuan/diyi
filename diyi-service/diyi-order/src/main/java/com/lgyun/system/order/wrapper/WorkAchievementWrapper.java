package com.lgyun.system.order.wrapper;


import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.WorkAchievementEntity;
import com.lgyun.system.order.vo.WorkAchievementVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-06-29 14:46:14
 */
public class WorkAchievementWrapper extends BaseEntityWrapper<WorkAchievementEntity, WorkAchievementVO> {

    public static WorkAchievementWrapper build() {
        return new WorkAchievementWrapper();
    }

	@Override
	public WorkAchievementVO entityVO(WorkAchievementEntity workAchievement) {
			WorkAchievementVO workAchievementVO = BeanUtil.copy(workAchievement, WorkAchievementVO.class);

		return workAchievementVO;
	}

}
