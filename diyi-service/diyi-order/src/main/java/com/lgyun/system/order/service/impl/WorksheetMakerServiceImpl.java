package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.enumeration.WorksheetMakerState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.mapper.WorksheetMakerMapper;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Slf4j
@Service
@AllArgsConstructor
public class WorksheetMakerServiceImpl extends BaseServiceImpl<WorksheetMakerMapper, WorksheetMakerEntity> implements IWorksheetMakerService {

    private IUserClient iUserClient;

    @Override
    public int getWorksheetCount(Long worksheetId) {
        return baseMapper.getWorksheetCount(worksheetId);
    }

    @Override
    public R submitAchievement(WorksheetMakerEntity worksheetMakerEntity, String achievementDesc, String achievementFiles) {
        if(null == worksheetMakerEntity || null == achievementFiles || "" == achievementFiles){
            return R.fail("提交失败");
        }
        worksheetMakerEntity.setAchievementDesc(achievementDesc);
        worksheetMakerEntity.setAchievementFiles(achievementFiles);
        worksheetMakerEntity.setAchievementDate(new Date());
        worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.VERIFIED);
        saveOrUpdate(worksheetMakerEntity);
        return R.success("提交工作");
    }

    @Override
    public R checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Long enterpriseId,Boolean bool) {
        WorksheetMakerEntity worksheetMakerEntity = getById(worksheetMakerId);
        if(null == worksheetMakerEntity || null == checkMoney ||!worksheetMakerEntity.getWorksheetMakerState().equals(WorksheetMakerState.VERIFIED)){
            return R.fail("验收失败");
        }
        EnterpriseEntity byId = iUserClient.getEnterpriseById(enterpriseId);
        worksheetMakerEntity.setCheckDate(new Date());
        worksheetMakerEntity.setCheckMoney(checkMoney);
        worksheetMakerEntity.setCheckPerson(byId.getEnterpriseName());
        if(bool){
            worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.VALIDATION);
        }else{
            worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.FAILED);
        }
        saveOrUpdate(worksheetMakerEntity);
        return R.success("验收成功");
    }

    @Override
    public R<AllIncomeYearMonthVO> queryAllMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month) {
        return R.data(baseMapper.queryAllMoneyByYearMonth(worksheetType, makerType, makerId, year, month));
    }

    @Override
    public R<IncomeYearVO> queryMoneyByYear(WorkSheetType worksheetType, MakerType makerType, Long makerId) {
        return R.data(baseMapper.queryMoneyByYear(worksheetType, makerType, makerId));
    }

    @Override
    public R<IncomeMonthVO> queryMoneyByMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year) {
        return R.data(baseMapper.queryMoneyByMonth(worksheetType, makerType, makerId, year));
    }

    @Override
    public R<IPage<AllIncomeYearMonthEnterpriseVO>> queryAllMoneyByYearMonthEnterprise(IPage<AllIncomeYearMonthEnterpriseVO> page, WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month) {
        return R.data(page.setRecords(baseMapper.queryAllMoneyByYearMonthEnterprise(worksheetType, makerType, makerId, year, month, page)));
    }

    @Override
    public R<IPage<IncomeDetailYearMonthVO>> queryMoneyDetailByYearMonth(IPage<IncomeDetailYearMonthVO> page, WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId) {
        return R.data(page.setRecords(baseMapper.queryMoneyDetailByYearMonth(worksheetType, makerType, makerId, year, month, enterpriseId, page)));
    }

    @Override
    public R<BigDecimal> queryAllMoneyDetailByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId) {
        return R.data(baseMapper.queryAllMoneyDetailByYearMonth(worksheetType, makerType, makerId, year, month, enterpriseId));
    }

    @Override
    public Boolean isMakerId(Long makerId, Long worksheetId) {
        Boolean bool =true;
        WorksheetMakerEntity worksheetMakerEntity = baseMapper.isMakerId(makerId, worksheetId);
        if(null != worksheetMakerEntity){
            bool = false;
        }
        return bool;
    }
}
