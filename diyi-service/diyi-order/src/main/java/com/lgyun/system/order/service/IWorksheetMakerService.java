package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.IncomeYearMonthVO;

import java.math.BigDecimal;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public interface IWorksheetMakerService extends BaseService<WorksheetMakerEntity> {

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

    /**
     * 验收工作成果
     */
    R<IncomeYearMonthVO> queryMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);
}

