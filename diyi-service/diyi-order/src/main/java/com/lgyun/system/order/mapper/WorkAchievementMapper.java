package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.common.api.R;
import com.lgyun.system.order.entity.WorkAchievementEntity;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-06-29 14:46:14
 */
@Mapper
public interface WorkAchievementMapper extends BaseMapper<WorkAchievementEntity> {
//    /**
//     * 验收
//     */
//    R accepted(BigDecimal checkMoneyNum, Long workAchievementId);
}

