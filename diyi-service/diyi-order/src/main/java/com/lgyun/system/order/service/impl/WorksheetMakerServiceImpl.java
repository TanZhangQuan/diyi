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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@RequiredArgsConstructor
public class WorksheetMakerServiceImpl extends BaseServiceImpl<WorksheetMakerMapper, WorksheetMakerEntity> implements IWorksheetMakerService {

    @Autowired
    @Lazy
    private IWorksheetMakerService worksheetMakerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> submitAchievement(Long worksheetMakerId, String achievementDesc, String achievementFiles, IWorksheetService worksheetService) {

        WorksheetMakerEntity worksheetMakerEntity = worksheetMakerService.getById(worksheetMakerId);
        if (null == worksheetMakerEntity) {
            return R.fail("工单-创客不存在");
        }

        WorksheetEntity worksheetEntity = worksheetService.getById(worksheetMakerEntity.getWorksheetId());
        if (worksheetEntity == null){
            return R.fail("工单不存在");
        }

        if (!(worksheetEntity.getWorksheetState().equals(WorksheetState.CLOSED) || worksheetEntity.getWorksheetState().equals(WorksheetState.CHECKACCEPT))) {
            return R.fail("商户未关单，暂不能提交工作成果");
        }

        if (WorksheetState.CLOSED.equals(worksheetEntity.getWorksheetState())) {
            worksheetEntity.setWorksheetState(WorksheetState.CHECKACCEPT);
            worksheetService.updateById(worksheetEntity);
        }

        worksheetMakerEntity.setAchievementDesc(achievementDesc);
        worksheetMakerEntity.setAchievementFiles(achievementFiles);
        worksheetMakerEntity.setAchievementDate(new Date());
        worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.VERIFIED);
        updateById(worksheetMakerEntity);

        return R.success("提交工作");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Boolean bool) {
        WorksheetMakerEntity worksheetMakerEntity = getById(worksheetMakerId);
        if (null == worksheetMakerEntity) {
            return R.fail("工单-创客记录不存在");
        }

        if (!(WorksheetMakerState.VERIFIED.equals(worksheetMakerEntity.getWorksheetMakerState()) || WorksheetMakerState.SUBMITTED.equals(worksheetMakerEntity.getWorksheetMakerState()) || WorksheetMakerState.VALIDATION.equals(worksheetMakerEntity.getWorksheetMakerState()))) {
            return R.fail("状态有误");
        }

        worksheetMakerEntity.setCheckDate(new Date());
        worksheetMakerEntity.setCheckMoney(checkMoney);

        if (WorksheetMakerState.VALIDATION.equals(worksheetMakerEntity.getWorksheetMakerState())) {
            updateById(worksheetMakerEntity);
            return R.success("修改成功");
        }

        if (bool) {
            worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.VALIDATION);
        } else {
            worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.FAILED);
        }
        updateById(worksheetMakerEntity);

        return R.success("验收成功");
    }

    @Override
    public IPage<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId, IPage<WorksheetMakerDetailsVO> page) {
        return page.setRecords(baseMapper.getWorksheetMakerDetails(worksheetId, page));
    }

    @Override
    public List<WorksheetMakerDetailsVO> getWorksheetMakerDetails(Long worksheetId) {
        return baseMapper.getWorksheetMakerDetailList(worksheetId);
    }

    @Override
    public Integer getOrderGrabbingCount(Long worksheetId) {
        QueryWrapper<WorksheetMakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WorksheetMakerEntity::getWorksheetId, worksheetId);
        return baseMapper.selectCount(queryWrapper);
    }

}
