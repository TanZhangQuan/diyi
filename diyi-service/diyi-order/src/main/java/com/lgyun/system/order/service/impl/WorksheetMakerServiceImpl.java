package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetMakerState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.mapper.WorksheetMakerMapper;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Service
@AllArgsConstructor
public class WorksheetMakerServiceImpl extends BaseServiceImpl<WorksheetMakerMapper, WorksheetMakerEntity> implements IWorksheetMakerService {

    private IEnterpriseService enterpriseService;

    @Override
    public int getWorksheetCount(Long worksheetId) {
        return baseMapper.getWorksheetCount(worksheetId);
    }

    @Override
    public R submitAchievement(Long worksheetMakerId, String achievementDesc, String achievementFiles) {
        WorksheetMakerEntity worksheetMakerEntity = getById(worksheetMakerId);
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
        if(null == worksheetMakerEntity || null == checkMoney){
            return R.fail("验收失败");
        }
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
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
}
