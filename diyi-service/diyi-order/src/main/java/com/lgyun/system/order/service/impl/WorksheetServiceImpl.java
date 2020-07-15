package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.ReleaseWorksheetDto;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.mapper.WorksheetMapper;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.WorksheetXiaoVo;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Service
@AllArgsConstructor
public class WorksheetServiceImpl extends BaseServiceImpl<WorksheetMapper, WorksheetEntity> implements IWorksheetService {

    private IWorksheetMakerService worksheetMakerService;
    private IMakerService makerService;
    private IIndividualBusinessService individualBusinessService;
    private IIndividualEnterpriseService individualEnterpriseService;

    @Override
    @Transactional
    public R releaseWorksheet(ReleaseWorksheetDto releaseWorksheetDTO) {
        WorksheetEntity worksheetEntity = new WorksheetEntity();
        if(StringUtil.isBlank(releaseWorksheetDTO.getWorksheetName())){
            return R.fail("工单名称不能为空");
        }
        if(null == releaseWorksheetDTO.getEnterpriseId()){
            return R.fail("企业不能为空");
        }
        if(null == releaseWorksheetDTO.getMakerType()){
            return R.fail("创客类型不能为空");
        }
        if(null == releaseWorksheetDTO.getWorksheetType()){
            return R.fail("工单类型不能为空");
        }
        if(null == releaseWorksheetDTO.getWorksheetMode()){
            return R.fail("工单模式不能为空");
        }

        BeanUtil.copy(releaseWorksheetDTO, worksheetEntity);
        worksheetEntity.setWorksheetNo(UUID.randomUUID().toString());
        worksheetEntity.setPublishDate(new Date());
        worksheetEntity.setWorksheetState(WorksheetState.PUBLISHING);
        save(worksheetEntity);

        if(WorkSheetMode.BLEND.equals(releaseWorksheetDTO.getWorksheetMode()) || WorkSheetMode.DISPATCH.equals(releaseWorksheetDTO.getWorksheetMode())){
            String makerIds = releaseWorksheetDTO.getMakerIds();
            String[] split = makerIds.split(",");
            for (int i = 0; i < split.length; i++){
                WorksheetMakerEntity worksheetMakerEntity = new WorksheetMakerEntity();
                worksheetMakerEntity.setMakerId(Long.parseLong(split[0]));
                worksheetMakerEntity.setWorksheetId(worksheetEntity.getWorksheetId());
                worksheetMakerEntity.setGetType(GetType.GETDISPATCH);
                worksheetMakerEntity.setGetOrderDate(new Date());
                worksheetMakerEntity.setArrangePerson("xitong");
                worksheetMakerEntity.setArrangeDate(new Date());
                worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.SUBMITTED);
                worksheetMakerService.save(worksheetMakerEntity);
            }
        }
        return R.success("成功");
    }

    @Override
    public R orderGrabbing(Long worksheetId, Long makerId) {
        if(null == worksheetId || null == makerId){
            return R.fail("参数错误");
        }
        WorksheetEntity worksheetEntity = getById(worksheetId);
        MakerEntity makerEntity = makerService.getById(makerId);
        if(!worksheetEntity.getWorksheetState().equals(WorksheetState.PUBLISHING)){
            return R.fail("暂停抢单");
        }
        return orderGrabbing(worksheetEntity,makerEntity);
    }

    @Override
    public R<IPage<WorksheetXiaoVo>> findXiaoPage(IPage<WorksheetXiaoVo> page,Integer worksheetState) {
        if(worksheetState == 1){
            return R.data(page.setRecords(baseMapper.findXiaoPage(page,WorksheetState.PUBLISHING.getValue())));
        }
        if(worksheetState == 2){
            return R.data(page.setRecords(baseMapper.findXiaoPage2(page,WorksheetMakerState.SUBMITTED.getValue())));
        }
        if(worksheetState == 3){
            return R.data(page.setRecords(baseMapper.findXiaoPage3(page)));
        }
        return R.fail("失败");
    }

    public synchronized R orderGrabbing(WorksheetEntity worksheetEntity,MakerEntity makerEntity){
        int worksheetCount = worksheetMakerService.getWorksheetCount(worksheetEntity.getWorksheetId());
        if(worksheetCount == worksheetEntity.getUppersonNum()){
            if(worksheetEntity.getWorksheetState().equals(WorksheetState.PUBLISHING)){
                worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
                worksheetEntity.setCloseDesc("2");
                worksheetEntity.setCloseWorksheetDate(new Date());
                worksheetEntity.setClosePerson("系统");
                save(worksheetEntity);
            }
            return R.fail("工单已抢完");
        }
        IndividualBusinessEntity makerId = individualBusinessService.findMakerId(makerEntity.getMakerId());
        if(null == makerId && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALBUSINESS)){
            return R.fail("创客身份不符-个体");
        }
        IndividualEnterpriseEntity individualEnterpriseEntity = individualEnterpriseService.findMakerId(makerEntity.getMakerId());
        if(null == individualEnterpriseEntity && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALENTERPRISE)){
            return R.fail("创客身份不符-个独");
        }
        WorksheetMakerEntity worksheetMakerEntity = new WorksheetMakerEntity();
        worksheetMakerEntity.setMakerId(makerEntity.getMakerId());
        worksheetMakerEntity.setWorksheetId(worksheetEntity.getWorksheetId());
        worksheetMakerEntity.setGetType(GetType.GETGRABBING);
        worksheetMakerEntity.setGetOrderDate(new Date());
        worksheetMakerEntity.setMakerName(makerEntity.getName());
        worksheetMakerEntity.setArrangePerson("qiangdan");
        worksheetMakerEntity.setArrangeDate(new Date());
        worksheetMakerEntity.setWorksheetMakerState(WorksheetMakerState.SUBMITTED);
        worksheetMakerService.save(worksheetMakerEntity);
        return R.success("抢单成功");
    }
}
