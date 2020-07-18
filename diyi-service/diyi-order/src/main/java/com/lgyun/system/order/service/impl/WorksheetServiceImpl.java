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
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@Slf4j
@Service
@AllArgsConstructor
public class WorksheetServiceImpl extends BaseServiceImpl<WorksheetMapper, WorksheetEntity> implements IWorksheetService {

    private IWorksheetMakerService worksheetMakerService;
    private IUserClient iUserClient;

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
        if(!WorkSheetMode.GRABBING.equals(releaseWorksheetDTO.getWorksheetMode()) && StringUtil.isBlank(releaseWorksheetDTO.getMakerIds())){
            return R.fail("创客的ids不能为空");
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
                MakerEntity makerEntity = iUserClient.makerFindById(Long.parseLong(split[0]));
                worksheetMakerEntity.setMakerName(makerEntity.getName());
                worksheetMakerEntity.setWorksheetId(worksheetEntity.getId());
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
    @Transactional
    public R orderGrabbing(Long worksheetId, Long makerId) {
        if(null == worksheetId || null == makerId){
            return R.fail("参数错误");
        }
        WorksheetEntity worksheetEntity = getById(worksheetId);
        if(worksheetEntity.getWorksheetMode().equals(WorkSheetMode.DISPATCH)){
            return R.fail("该工单不支持抢单");
        }
        if(null == worksheetEntity){
            return R.fail("没有此工单");
        }
        MakerEntity makerEntity = iUserClient.makerFindById(makerId);
        if(null == worksheetEntity){
            return R.fail("没有此创客");
        }
        if(!worksheetEntity.getWorksheetState().equals(WorksheetState.PUBLISHING)){
            return R.fail("暂停抢单");
        }
        int worksheetCount = worksheetMakerService.getWorksheetCount(worksheetEntity.getId());
        Boolean bool = worksheetMakerService.isMakerId(makerId, worksheetId);
        if(!bool){
            return R.fail("此工单，你已经抢过了");
        }
        return orderGrabbing(worksheetEntity,makerEntity,worksheetCount);
    }

    @Override
    public R<IPage<WorksheetXiaoVo>> findXiaoPage(IPage<WorksheetXiaoVo> page,Integer worksheetState,Long makerId) {
        if(worksheetState == 1){
            return R.data(page.setRecords(baseMapper.findXiaoPage(page,makerId)));
        }
        if(worksheetState == 2){
            return R.data(page.setRecords(baseMapper.findXiaoPage2(page,makerId)));
        }
        if(worksheetState == 3){
            return R.data(page.setRecords(baseMapper.findXiaoPage3(page,makerId)));
        }
        return R.fail("失败");
    }

    public synchronized R orderGrabbing(WorksheetEntity worksheetEntity,MakerEntity makerEntity,int worksheetCount){

        if(worksheetCount == worksheetEntity.getUppersonNum()){
            if(worksheetEntity.getWorksheetState().equals(WorksheetState.PUBLISHING)){
                worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
                worksheetEntity.setCloseDesc("2");
                worksheetEntity.setCloseWorksheetDate(new Date());
                worksheetEntity.setClosePerson("系统");
                saveOrUpdate(worksheetEntity);
            }
            return R.fail("工单已抢完");
        }
        List<IndividualBusinessEntity> individualBusinessEntities = iUserClient.individualBusinessByMakerId(makerEntity.getId());
        if((null == individualBusinessEntities || individualBusinessEntities.size() <= 0) && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALBUSINESS)){
            return R.fail("创客身份不符-个体");
        }
        List<IndividualEnterpriseEntity> individualEnterpriseEntities = iUserClient.individualEnterpriseFindByMakerId(makerEntity.getId());
        if((null == individualEnterpriseEntities || individualEnterpriseEntities.size() <= 0) && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALENTERPRISE)){
            return R.fail("创客身份不符-个独");
        }
        WorksheetMakerEntity worksheetMakerEntity = new WorksheetMakerEntity();
        worksheetMakerEntity.setMakerId(makerEntity.getId());
        worksheetMakerEntity.setWorksheetId(worksheetEntity.getId());
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
