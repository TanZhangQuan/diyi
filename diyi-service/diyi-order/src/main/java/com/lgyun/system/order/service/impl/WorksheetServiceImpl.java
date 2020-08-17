package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Service 实现
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
    public R<String> releaseWorksheet(ReleaseWorksheetDto releaseWorksheetDTO) {
        WorksheetEntity worksheetEntity = new WorksheetEntity();
        if (StringUtil.isBlank(releaseWorksheetDTO.getWorksheetName())) {
            return R.fail("工单名称不能为空");
        }
        if (null == releaseWorksheetDTO.getEnterpriseId()) {
            return R.fail("企业不能为空");
        }
        if (null == releaseWorksheetDTO.getMakerType()) {
            return R.fail("创客类型不能为空");
        }
        if (null == releaseWorksheetDTO.getWorksheetType()) {
            return R.fail("工单类型不能为空");
        }
        if (null == releaseWorksheetDTO.getWorksheetMode()) {
            return R.fail("工单模式不能为空");
        }
        if (!WorkSheetMode.GRABBING.equals(releaseWorksheetDTO.getWorksheetMode()) && StringUtil.isBlank(releaseWorksheetDTO.getMakerIds())) {
            return R.fail("创客的ids不能为空");
        }
        BeanUtil.copy(releaseWorksheetDTO, worksheetEntity);
        worksheetEntity.setWorksheetNo(UUID.randomUUID().toString());
        save(worksheetEntity);
        if (WorkSheetMode.BLEND.equals(releaseWorksheetDTO.getWorksheetMode()) || WorkSheetMode.DISPATCH.equals(releaseWorksheetDTO.getWorksheetMode())) {
            String makerIds = releaseWorksheetDTO.getMakerIds();
            String[] split = makerIds.split(",");
            for (int i = 0; i < split.length; i++) {
                WorksheetMakerEntity worksheetMakerEntity = new WorksheetMakerEntity();
                worksheetMakerEntity.setMakerId(Long.parseLong(split[0]));
                MakerEntity makerEntity = iUserClient.makerFindById(Long.parseLong(split[0]));
                worksheetMakerEntity.setMakerName(makerEntity.getName());
                worksheetMakerEntity.setWorksheetId(worksheetEntity.getId());
                worksheetMakerEntity.setGetType(GetType.GETDISPATCH);
                worksheetMakerEntity.setGetOrderDate(new Date());
                worksheetMakerEntity.setArrangePerson("xitong");
                worksheetMakerEntity.setArrangeDate(new Date());
                worksheetMakerService.save(worksheetMakerEntity);
            }
        }
        return R.success("成功");
    }

    @Override
    @Transactional
    public R<String> orderGrabbing(Long worksheetId, Long makerId) {
        if (null == worksheetId || null == makerId) {
            return R.fail("参数错误");
        }
        WorksheetEntity worksheetEntity = getById(worksheetId);
        if (worksheetEntity.getWorksheetMode().equals(WorkSheetMode.DISPATCH)) {
            return R.fail("该工单不支持抢单");
        }
        if (null == worksheetEntity) {
            return R.fail("没有此工单");
        }
        MakerEntity makerEntity = iUserClient.makerFindById(makerId);
        if (null == worksheetEntity) {
            return R.fail("没有此创客");
        }
        if (!worksheetEntity.getWorksheetState().equals(WorksheetState.PUBLISHING)) {
            return R.fail("暂停抢单");
        }
        int worksheetCount = worksheetMakerService.getWorksheetCount(worksheetEntity.getId());
        Boolean bool = worksheetMakerService.isMakerId(makerId, worksheetId);
        if (!bool) {
            return R.fail("此工单，你已经抢过了");
        }
        return orderGrabbing(worksheetEntity, makerEntity, worksheetCount);
    }

    @Override
    public R<IPage<WorksheetXiaoVo>> findXiaoPage(IPage<WorksheetXiaoVo> page, Integer worksheetState, Long makerId) {
        if (worksheetState == 1) {
            return R.data(page.setRecords(baseMapper.findXiaoPage(page, makerId)));
        }
        if (worksheetState == 2) {
            return R.data(page.setRecords(baseMapper.findXiaoPage2(page, makerId)));
        }
        if (worksheetState == 3) {
            return R.data(page.setRecords(baseMapper.findXiaoPage3(page, makerId)));
        }
        return R.fail("失败");
    }

    @Override
    public R<WorksheetXiaoVo> getWorksheetDetails(Long worksheetMakerId) {
        return R.data(baseMapper.getWorksheetDetails(worksheetMakerId));
    }

    @Override
    public R getEnterpriseWorksheet(IPage<WorksheetXiaoVo> page, Long enterpriseId, WorksheetState worksheetState, String worksheetNo, String worksheetName, String startTime, String endTime) {
        return R.data(baseMapper.getEnterpriseWorksheet(enterpriseId, worksheetState, worksheetNo, worksheetName, startTime, endTime, page));
    }

    @Override
    public R getWorksheetWebDetails(IPage<WorksheetMakerDetailsVO> page, Long worksheetId) {
        WorksheetEntity worksheetEntity = getById(worksheetId);
        WorksheetXiaoVo worksheetXiaoVo = BeanUtil.copy(worksheetEntity, WorksheetXiaoVo.class);
        worksheetXiaoVo.setWorksheetMakerId(worksheetId);
        Map map = new HashMap();
        map.put("worksheetXiaoVo", worksheetXiaoVo);

        IPage<WorksheetMakerDetailsVO> worksheetMakerDetails = worksheetMakerService.getWorksheetMakerDetails(worksheetId, page);
        List<WorksheetMakerDetailsVO> records = worksheetMakerDetails.getRecords();
        for (WorksheetMakerDetailsVO worksheetMakerDetailsVO : records) {
            if (SignState.SIGNED.equals(worksheetMakerDetailsVO.getJoinSignState()) && SignState.SIGNED.equals(worksheetMakerDetailsVO.getEmpowerSignState())) {
                worksheetMakerDetailsVO.setProtocolAuthentication(CertificationState.CERTIFIED);
            }
            if (VerifyStatus.VERIFYPASS.equals(worksheetMakerDetailsVO.getBankCardVerifyStatus())) {
                worksheetMakerDetailsVO.setRealNameAuthentication(CertificationState.CERTIFIED);
            }
        }
        map.put("worksheetMakerDetails",worksheetMakerDetails);
        return R.data(map);
    }

    @Override
    public R closeOrOpen(Long worksheetId, Integer variable) {
        WorksheetEntity worksheetEntity = getById(worksheetId);
        if (variable != 1 && variable != 2) {
            R.fail("参数有误");
        }
        if (1 == variable) {
            worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
            return R.success("关闭成功");
        } else {
            worksheetEntity.setWorksheetState(WorksheetState.PUBLISHING);
            return R.success("开启成功");
        }
    }

    @Override
    public R kickOut(Long worksheetId, Long makerId) {
        WorksheetMakerEntity worksheetMakerEntity = worksheetMakerService.getmakerIdAndWorksheetId(worksheetId, makerId);
        if (null == worksheetMakerEntity) {
           return R.fail("创客没有抢单记录");
        }
        removeById(worksheetMakerEntity.getId());
        return R.success("移除成功");
    }

    @Override
    public R checkAccept(Long worksheetMakerId, BigDecimal checkMoney) {
        WorksheetMakerEntity worksheetMakerEntity = worksheetMakerService.getById(worksheetMakerId);
        WorksheetEntity worksheetEntity = getById(worksheetMakerEntity.getWorksheetId());
        if (!worksheetEntity.getWorksheetState().equals(WorksheetState.CLOSED)) {
            R.fail("工单还没有关单");
        }
        worksheetMakerEntity.setCheckMoney(checkMoney);
        worksheetMakerEntity.setCheckDate(new Date());
        worksheetMakerEntity.setCheckPerson("商户");
        worksheetMakerService.saveOrUpdate(worksheetMakerEntity);
        return R.success("验收成功");
    }

    @Override
    public R<IPage<EnterpriseWorksheetDetailVo>> getWorksheetDetailsByMaker(IPage<EnterpriseWorksheetDetailVo> page, Long enterpriseId, Long makerId) {
        return R.data(page.setRecords(baseMapper.getWorksheetDetailsByMaker(enterpriseId, makerId, page)));
    }

    @Override
    public R<IPage<WorksheetByEnterpriseVO>> getWorksheetByEnterpriseId(Long enterpriseId, WorkSheetType workSheetType, String worksheetNo, String worksheetName, IPage<WorksheetByEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getWorksheetByEnterpriseId(enterpriseId, workSheetType, worksheetNo, worksheetName, page)));
    }

    @Override
    public R<WorksheetNoIdVO> getByWorksheetNo(String worksheetNo) {
        QueryWrapper<WorksheetEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WorksheetEntity::getWorksheetNo, worksheetNo);

        WorksheetEntity worksheetEntity = baseMapper.selectOne(queryWrapper);
        if (worksheetEntity == null) {
            return R.fail("工单不存在");
        }

        return R.data(BeanUtil.copy(worksheetEntity, WorksheetNoIdVO.class));
    }

    @Override
    public R<WorksheetNoIdVO> getByWorksheetId(String worksheetId) {

        QueryWrapper<WorksheetEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WorksheetEntity::getId, worksheetId);

        WorksheetEntity worksheetEntity = baseMapper.selectOne(queryWrapper);
        if (worksheetEntity == null) {
            return R.fail("工单不存在");
        }

        return R.data(BeanUtil.copy(worksheetEntity, WorksheetNoIdVO.class));
    }

    public synchronized R<String> orderGrabbing(WorksheetEntity worksheetEntity, MakerEntity makerEntity, int worksheetCount) {

        if (worksheetCount == worksheetEntity.getUppersonNum()) {
            if (worksheetEntity.getWorksheetState().equals(WorksheetState.PUBLISHING)) {
                worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
                worksheetEntity.setCloseDesc("2");
                worksheetEntity.setCloseWorksheetDate(new Date());
                worksheetEntity.setClosePerson("系统");
                saveOrUpdate(worksheetEntity);
            }
            return R.fail("工单已抢完");
        }
        List<IndividualBusinessEntity> individualBusinessEntities = iUserClient.individualBusinessByMakerId(makerEntity.getId());
        if ((null == individualBusinessEntities || individualBusinessEntities.size() <= 0) && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALBUSINESS)) {
            return R.fail("创客身份不符-个体");
        }
        List<IndividualEnterpriseEntity> individualEnterpriseEntities = iUserClient.individualEnterpriseFindByMakerId(makerEntity.getId());
        if ((null == individualEnterpriseEntities || individualEnterpriseEntities.size() <= 0) && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALENTERPRISE)) {
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
        worksheetMakerService.save(worksheetMakerEntity);
        return R.success("抢单成功");
    }
}
