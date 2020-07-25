package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * Mapper
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Mapper
public interface WorksheetMakerMapper extends BaseMapper<WorksheetMakerEntity> {

    /**
     * 根据工单id查询抢单条数
     *
     * @param worksheetId
     * @return
     */
    int getWorksheetCount(Long worksheetId);

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
    AllIncomeYearMonthVO queryAllMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据工单类型，创客类型查询每年收入
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @return
     */
    IncomeYearVO queryMoneyByYear(WorkSheetType worksheetType, MakerType makerType, Long makerId);

    /**
     * 根据工单类型，创客类型，年份查询每月收入
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @param year
     * @return
     */
    IncomeMonthVO queryMoneyByMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year);

    /**
     * 根据工单类型，创客类型，年份，月份（可选）查询创客对应商户的总收入金额
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param page
     * @return
     */
    List<AllIncomeYearMonthEnterpriseVO> queryAllMoneyByYearMonthEnterprise(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询收入明细
     *
     * @param worksheetType
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @param page
     * @return
     */
    List<IncomeDetailYearMonthVO> queryMoneyDetailByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

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
    BigDecimal queryAllMoneyDetailByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

}

