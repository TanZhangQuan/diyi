package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
public interface IWorksheetMakerService extends BaseService<WorksheetMakerEntity> {

    /**
     * 根据工单id查询抢单条数
     *
     * @param worksheetId
     * @return
     */
    int getWorksheetCount(Long worksheetId);

    /**
     * 提交工作成果
     *
     * @param worksheetMakerEntity
     * @param achievementDesc
     * @param achievementFiles
     * @return
     */
    R<String> submitAchievement(WorksheetMakerEntity worksheetMakerEntity, String achievementDesc, String achievementFiles);


    /**
     * 验收工作成果
     *
     * @param worksheetMakerId
     * @param checkMoney
     * @param enterpriseId
     * @param bool
     * @return
     */
    R<String> checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Long enterpriseId, Boolean bool);

    /**
     * 查询创客有没有抢单
     *
     * @param makerId
     * @param worksheetId
     * @return
     */
    Boolean isMakerId(Long makerId, Long worksheetId);

    /**
     * 根据工单类型，创客类型，年份，月份（可选）查询工单笔数和总收入金额
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    R<AllIncomeYearMonthVO> queryAllMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据工单类型，创客类型查询每年收入
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @return
     */
    R<IncomeYearVO> queryMoneyByYear(WorkSheetType worksheetType, MakerType makerType, Long makerId);

    /**
     * 根据工单类型，创客类型，年份查询每月收入
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @param year
     * @return
     */
    R<TransactionMonthVO> queryMoneyByMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year);

    /**
     * 根据工单类型，创客类型，年份，月份（可选）查询创客对应商户的总收入金额
     *
     * @param page
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    R<IPage<AllIncomeYearMonthEnterpriseVO>> queryAllMoneyByYearMonthEnterprise(IPage<AllIncomeYearMonthEnterpriseVO> page, WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细
     *
     * @param page
     * @param worksheetType
     * @param makerType
     * @param id
     * @param year
     * @param month
     * @param enterpriseId
     * @return
     */
    R<IPage<IncomeDetailYearMonthVO>> queryMoneyDetailByYearMonth(IPage<IncomeDetailYearMonthVO> page, WorkSheetType worksheetType, MakerType makerType, Long id, Long year, Long month, Long enterpriseId);

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @return
     */
    R<BigDecimal> queryAllMoneyDetailByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @param page
     * @return
     */
    IPage<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId, IPage<WorksheetMakerDetailsVO> page);


    /**
     * 查询创客有没有抢单
     *
     * @param makerId
     * @param worksheetId
     * @return
     */
    WorksheetMakerEntity getmakerIdAndWorksheetId(Long makerId, Long worksheetId);

    /**
     * 根据支付清单ID获取创客工单关联
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    R<IPage<WorksheetMakerListVO>> getByPayEnterpriseId(Long payEnterpriseId, IPage<WorksheetMakerListVO> page);


    /**
     * 根据工单id查询所有的创客明细
     *
     * @param worksheetId
     * @return
     */
    List<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId);
}

