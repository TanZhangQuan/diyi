package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelType;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.entity.WorkAchievementEntity;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.mapper.WorkAchievementMapper;
import com.lgyun.system.order.service.IOrderService;
import com.lgyun.system.order.service.IWorkAchievementService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-06-29 14:46:14
 */
@Service("workAchievementService")
public class WorkAchievementServiceImpl extends ServiceImpl<WorkAchievementMapper, WorkAchievementEntity> implements IWorkAchievementService {

    @Autowired
    IMakerEnterpriseService iMakerEnterpriseService;

    @Autowired
    IWorkAchievementService iWorkAchievementService;

    @Autowired
    IWorksheetService iWorksheetService;

    @Autowired
    IOrderService iOrderService;

    @Autowired
    IEnterpriseService iEnterpriseService;

    @Autowired
    IMakerService iMakerService;

    @Override
    public R accepted(BigDecimal checkMoneyNum, Long workAchievementId,Long positionId) {
        if(null == positionId){
            return R.fail("验收失败0");
        }
        WorkAchievementEntity workAchievementEntity = iWorkAchievementService.getById(workAchievementId);
        if(null == workAchievementEntity){
            return R.fail("验收失败1");
        }
        WorksheetEntity worksheetEntity = iWorksheetService.getById(workAchievementEntity.getWorksheetId());
        if(null == worksheetEntity){
            return R.fail("验收失败2");
        }
        OrderEntity orderEntity = iOrderService.getById(worksheetEntity.getOrderId());
        if(null == orderEntity){
            return R.fail("验收失败3");
        }
        EnterpriseEntity enterpriseEntity = iEnterpriseService.getById(orderEntity.getEnterpriseId());
        if(null == enterpriseEntity){
            return R.fail("验收失败4");
        }
        MakerEntity makerEntity = iMakerService.getById(worksheetEntity.getMakerId());
        if(null == makerEntity){
            return R.fail("验收失败5");
        }
        List<MakerEnterpriseEntity> makerId = iMakerEnterpriseService.getMakerId(makerEntity.getMakerId());
        boolean bool = false;
        if(null == makerId || makerId.size() <= 0){
            bool = true;
        }
        MakerEnterpriseEntity makerEnterpriseEntity = new MakerEnterpriseEntity();
        makerEnterpriseEntity.setEnterpriseId(enterpriseEntity.getEnterpriseId());
        makerEnterpriseEntity.setMakerId(makerEntity.getMakerId());
        makerEnterpriseEntity.setPositionId(positionId);
        makerEnterpriseEntity.setRelDate(new Date());
        makerEnterpriseEntity.setRelType(RelType.MAKERREL);
        makerEnterpriseEntity.setRelMemo("创客主动关联");
        makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
        makerEnterpriseEntity.setFirstCooperation(bool);
        makerEnterpriseEntity.setCooperationStartTime(new Date());
        iMakerEnterpriseService.save(makerEnterpriseEntity);
        return R.success("验收成功");
    }
}
