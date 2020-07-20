package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.AllIncomeYearMonthVO;
import com.lgyun.system.order.vo.IncomeMonthVO;
import com.lgyun.system.order.vo.IncomeYearVO;

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
    R submitAchievement(WorksheetMakerEntity worksheetMakerEntity,String achievementDesc,String achievementFiles);


    /**
     * 验收工作成果
     */
    R checkAchievement(Long worksheetMakerId, BigDecimal checkMoney,Long enterpriseId,Boolean bool);

    /**
     * 查询创客有没有抢单
     */
    Boolean isMakerId(Long makerId,Long worksheetId);

    /**
     * 根据工单类型，创客类型，年份或月份查询总收入
     */
    R<AllIncomeYearMonthVO> queryAllMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据工单类型，创客类型，年份查询每月收入
     */
    R<IncomeYearVO> queryMoneyByYear(WorkSheetType worksheetType, MakerType makerType, Long makerId);

    /**
     * 根据工单类型，创客类型，年份查询每月收入
     */
    R<IncomeMonthVO> queryMoneyByMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year);

}

