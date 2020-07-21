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
 *  Mapper
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Mapper
public interface WorksheetMakerMapper extends BaseMapper<WorksheetMakerEntity> {

    int getWorksheetCount(Long worksheetId);

    AllIncomeYearMonthVO queryAllMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month);

    WorksheetMakerEntity isMakerId(Long makerId,Long worksheetId);

    IncomeYearVO queryMoneyByYear(WorkSheetType worksheetType, MakerType makerType, Long makerId);

    IncomeMonthVO queryMoneyByMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year);

    List<AllIncomeYearMonthEnterpriseVO> queryAllMoneyByYearMonthEnterprise(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    List<IncomeDetailYearMonthVO> queryMoneyDetailByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

    BigDecimal queryAllMoneyDetailByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

}

