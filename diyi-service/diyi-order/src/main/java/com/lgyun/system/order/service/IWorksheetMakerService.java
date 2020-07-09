package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.order.entity.WorksheetMakerEntity;

import java.math.BigDecimal;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public interface IWorksheetMakerService extends IService<WorksheetMakerEntity> {

    /**
     * 根据工单id查询抢单条数
     */
    int getWorksheetCount(Long worksheetId);


    /**
     * 提交工作成果
     */
    R submitAchievement(Long worksheetMakerId,String achievementDesc,String achievementFiles);


    /**
     * 验收工作成果
     */
    R checkAchievement(Long worksheetMakerId, BigDecimal checkMoney,Long enterpriseId,Boolean bool);
}
