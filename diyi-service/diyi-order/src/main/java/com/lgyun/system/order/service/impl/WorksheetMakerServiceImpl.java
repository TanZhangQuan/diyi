package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.enumeration.WorksheetMakerState;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.mapper.WorksheetMakerMapper;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.vo.IncomeYearMonthVO;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public R<IncomeYearMonthVO> queryMoneyByYearMonth(WorkSheetType worksheetType, MakerType makerType, Long makerId, Long year, Long month) {
        return R.data(baseMapper.queryMoneyByYearMonth(worksheetType, makerType, makerId, year, month));
    }

    @Override
    public Boolean isMakerId(Long makerId, Long worksheetId) {
        Boolean bool =true;
        int count = baseMapper.isMakerId(makerId, worksheetId);
        if(count > 0){
            bool = false;
        }
        return bool;
    }
}
