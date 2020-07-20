package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.vo.AllIncomeYearMonthVO;
import com.lgyun.system.order.vo.IncomeMonthVO;
import com.lgyun.system.order.vo.IncomeYearVO;
import org.apache.ibatis.annotations.Mapper;

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

    int isMakerId(Long makerId,Long worksheetId);

    IncomeYearVO queryMoneyByYear(WorkSheetType worksheetType, MakerType makerType, Long makerId);

    IncomeMonthVO queryMoneyByMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year);

}

