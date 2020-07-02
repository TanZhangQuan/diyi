package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.order.entity.WorkAchievementEntity;

import java.math.BigDecimal;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-06-29 14:46:14
 */
public interface IWorkAchievementService extends IService<WorkAchievementEntity> {
    /**
     * 验收
     */
    R accepted(BigDecimal checkMoneyNum,Long workAchievementId,Long positionId);
}

