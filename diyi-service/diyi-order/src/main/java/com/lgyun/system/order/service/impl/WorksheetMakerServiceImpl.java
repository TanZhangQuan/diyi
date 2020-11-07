package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.WorksheetMakerState;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.mapper.WorksheetMakerMapper;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.WorksheetMakerDetailsVO;
import com.lgyun.system.order.vo.WorksheetMakerListVO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service 实现
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
    @Transactional(rollbackFor = Exception.class)
    public R<String> submitAchievement(WorksheetMakerEntity worksheetMakerEntity, String achievementDesc, String achievementFiles,IWorksheetService worksheetService) {
        if (null == worksheetMakerEntity || null == achievementFiles || "" == achievementFiles) {
            return R.fail("提交失败");
        }
        worksheetMakerEntity.setAchievementDesc(achievementDesc);
        worksheetMakerEntity.setAchievementFiles(achievementFiles);
        worksheetMakerEntity.setAchievementDate(new Date());
        worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.VERIFIED);
        saveOrUpdate(worksheetMakerEntity);
        WorksheetEntity byId = worksheetService.getById(worksheetMakerEntity.getWorksheetId());
        if(null == byId){
            return R.fail("提交失败");
        }
        if(WorksheetState.CLOSED.equals(byId.getWorksheetState())){
            byId.setWorksheetState(WorksheetState.CHECKACCEPT);
            worksheetService.saveOrUpdate(byId);
        }
        return R.success("提交工作");
    }

    @Override
    public R<String> checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Long enterpriseId, Boolean bool) {
        WorksheetMakerEntity worksheetMakerEntity = getById(worksheetMakerId);
        if (null == worksheetMakerEntity) {
            return R.fail("数据不存在");
        }
        if(null == checkMoney){
            return R.fail("验收金额不能为空");
        }
        if(!(WorksheetMakerState.VERIFIED.equals(worksheetMakerEntity.getWorksheetMakerState()) || WorksheetMakerState.VALIDATION.equals(worksheetMakerEntity.getWorksheetMakerState()))){
            return R.fail("状态不对");
        }
        EnterpriseEntity byId = iUserClient.queryEnterpriseById(enterpriseId);
        if(WorksheetMakerState.VALIDATION.equals(worksheetMakerEntity.getWorksheetMakerState())){
            worksheetMakerEntity.setCheckDate(new Date());
            worksheetMakerEntity.setCheckMoney(checkMoney);
            saveOrUpdate(worksheetMakerEntity);
            return R.success("修改成功");
        }
        worksheetMakerEntity.setCheckDate(new Date());
        worksheetMakerEntity.setCheckMoney(checkMoney);
        worksheetMakerEntity.setCheckPerson(byId.getEnterpriseName());
        if (bool) {
            worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.VALIDATION);
        } else {
            worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.FAILED);
        }
        saveOrUpdate(worksheetMakerEntity);

        return R.success("验收成功");
    }

    @Override
    public IPage<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId, IPage<WorksheetMakerDetailsVO> page) {
        return page.setRecords(baseMapper.getWorksheetMakerDetails(worksheetId,page));
    }

    @Override
    public WorksheetMakerEntity getmakerIdAndWorksheetId(Long makerId, Long worksheetId) {
        QueryWrapper<WorksheetMakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WorksheetMakerEntity::getMakerId, makerId)
                .eq(WorksheetMakerEntity::getWorksheetId, worksheetId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<WorksheetMakerListVO>> getByPayEnterpriseId(Long payEnterpriseId, IPage<WorksheetMakerListVO> page) {
        return R.data(page.setRecords(baseMapper.getByPayEnterpriseId(payEnterpriseId, page)));
    }

    @Override
    public List<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId) {
        return baseMapper.getWorksheetMakerDetails(worksheetId);
    }

}
