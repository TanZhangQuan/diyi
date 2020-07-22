package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.*;

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
     * 根据工单类型，创客类型，年份，月份（可选）查询工单笔数和总收入金额
     */
    R<AllIncomeYearMonthVO> queryAllMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据工单类型，创客类型查询每年收入
     */
    R<IncomeYearVO> queryMoneyByYear(WorkSheetType worksheetType, MakerType makerType, Long makerId);

    /**
     * 根据工单类型，创客类型，年份查询每月收入
     */
    R<IncomeMonthVO> queryMoneyByMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year);

    /**
     * 根据工单类型，创客类型，年份，月份（可选）查询创客对应商户的总收入金额
     */
    R<IPage<AllIncomeYearMonthEnterpriseVO>> queryAllMoneyByYearMonthEnterprise(IPage<AllIncomeYearMonthEnterpriseVO> page, WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细
     */
    R<IPage<IncomeDetailYearMonthVO>> queryMoneyDetailByYearMonth(IPage<IncomeDetailYearMonthVO> page, WorkSheetType worksheetType, MakerType makerType, Long id, Long year, Long month, Long enterpriseId);

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入
     */
    R<BigDecimal> queryAllMoneyDetailByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

}

