package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.SnowflakeIdWorker;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.ReleaseWorksheetDTO;
import com.lgyun.system.order.dto.WorksheetFinishedListDTO;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.mapper.WorksheetMapper;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.EnterpriseEntity;
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
    @Transactional(rollbackFor = Exception.class)
    public R<String> releaseWorksheet(ReleaseWorksheetDTO releaseWorksheetDTO) {
        WorksheetEntity worksheetEntity = new WorksheetEntity();
        if (StringUtil.isBlank(releaseWorksheetDTO.getWorksheetName())) {
            return R.fail("请输入工单名称");
        }
        if (null == releaseWorksheetDTO.getEnterpriseId()) {
            return R.fail("请选择商户");
        }
        EnterpriseEntity enterpriseEntity = iUserClient.queryEnterpriseById(releaseWorksheetDTO.getEnterpriseId());
        if(!AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState())){
            return R.fail("商户被冻结，请联系管理员！！");
        }
        if(!(releaseWorksheetDTO.getWorksheetFeeHigh().compareTo(BigDecimal.ZERO) == 0 && releaseWorksheetDTO.getWorksheetFeeLow().compareTo(BigDecimal.ZERO) > 0) && releaseWorksheetDTO.getWorksheetFeeHigh().compareTo(releaseWorksheetDTO.getWorksheetFeeLow()) < 1){
            return R.fail("最高费用不能比最低费用低");
        }
        if (null == releaseWorksheetDTO.getMakerType()) {
            return R.fail("请选择创客类型");
        }
        if (null == releaseWorksheetDTO.getWorksheetType()) {
            return R.fail("请选择工单类型");
        }
        if (null == releaseWorksheetDTO.getWorksheetMode()) {
            return R.fail("请选择工单模式");
        }
        if (!WorksheetMode.GRABBING.equals(releaseWorksheetDTO.getWorksheetMode()) && StringUtil.isBlank(releaseWorksheetDTO.getMakerIds())) {
            return R.fail("请选择创客");
        }
        if (releaseWorksheetDTO.getWorksheetMode().equals("DISPATCH") && releaseWorksheetDTO.getUpPersonNum() != 0 && releaseWorksheetDTO.getMakerIds().split(",").length != releaseWorksheetDTO.getUpPersonNum()) {
            return R.fail("工单创建失败，上限人数为" + releaseWorksheetDTO.getUpPersonNum() + ",创客数为" + releaseWorksheetDTO.getMakerIds().split(",").length);
        }
        if (releaseWorksheetDTO.getWorksheetMode().equals("BLEND") && releaseWorksheetDTO.getUpPersonNum() != 0 && releaseWorksheetDTO.getMakerIds().split(",").length < releaseWorksheetDTO.getUpPersonNum()) {
            return R.fail("工单创建失败，上限人数为" + releaseWorksheetDTO.getUpPersonNum() + ",创客数为" + releaseWorksheetDTO.getMakerIds().split(",").length);
        }
        String makerIds = releaseWorksheetDTO.getMakerIds();
        String[] split = makerIds.split(",");
        Set<String> sameSet = new HashSet<>();
        for (String element : split) {
            sameSet.add(element);
        }
        if (sameSet.size() != split.length) {
            return R.fail("有存在相同的指定创客");
        }
        BeanUtil.copy(releaseWorksheetDTO, worksheetEntity);
        if (null == releaseWorksheetDTO.getUpPersonNum()) {
            worksheetEntity.setUpPersonNum(0);
        }
        if (null == releaseWorksheetDTO.getWorkDays()) {
            worksheetEntity.setWorkDays(0);
        }
        if (null == releaseWorksheetDTO.getWorksheetFeeLow()) {
            worksheetEntity.setWorksheetFeeLow(new BigDecimal("0"));
        }
        if (null == releaseWorksheetDTO.getWorksheetFeeHigh()) {
            worksheetEntity.setWorksheetFeeHigh(new BigDecimal("0"));
        }
        if((WorksheetMode.BLEND.equals(releaseWorksheetDTO.getWorksheetMode()) || WorksheetMode.DISPATCH.equals(releaseWorksheetDTO.getWorksheetMode())) && releaseWorksheetDTO.getUpPersonNum() != 0 && releaseWorksheetDTO.getUpPersonNum() == split.length){
            worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
        }
        worksheetEntity.setWorksheetNo(SnowflakeIdWorker.getSerialNumber());
        save(worksheetEntity);
        if (WorksheetMode.BLEND.equals(releaseWorksheetDTO.getWorksheetMode()) || WorksheetMode.DISPATCH.equals(releaseWorksheetDTO.getWorksheetMode())) {
            for (int i = 0; i < split.length; i++) {
                WorksheetMakerEntity worksheetMakerEntity = new WorksheetMakerEntity();
                worksheetMakerEntity.setMakerId(Long.parseLong(split[i]));
                MakerEntity makerEntity = iUserClient.queryMakerById(Long.parseLong(split[i]));
                worksheetMakerEntity.setMakerName(makerEntity.getName());
                worksheetMakerEntity.setWorksheetId(worksheetEntity.getId());
                worksheetMakerEntity.setGetType(GetType.GETDISPATCH);
                worksheetMakerEntity.setGetOrderDate(new Date());
                worksheetMakerEntity.setArrangePerson("系统");
                worksheetMakerEntity.setArrangeDate(new Date());
                worksheetMakerService.save(worksheetMakerEntity);
            }
        }

        return R.success("发布成功");
    }

    @Override
    public R<IPage<WorksheetXiaoVO>> findXiaoPage(IPage<WorksheetXiaoVO> page, Integer worksheetState, Long makerId) {
        if (worksheetState == 1) {
            return R.data(page.setRecords(baseMapper.findXiaoPage(page, makerId)));
        } else if (worksheetState == 2) {
            return R.data(page.setRecords(baseMapper.findXiaoPage2(page, makerId)));
        } else if (worksheetState == 3) {
            return R.data(page.setRecords(baseMapper.findXiaoPage3(page, makerId)));
        } else {
            return R.fail("失败");
        }
    }

    @Override
    public R<WorksheetXiaoVO> getWorksheetDetails(Long worksheetMakerId) {
        return R.data(baseMapper.getWorksheetDetails(worksheetMakerId));
    }

    @Override
    public R getEnterpriseWorksheet(IPage<WorksheetXiaoVO> page, Long enterpriseId, WorksheetState worksheetState, String worksheetNo, String worksheetName, String startTime, String endTime) {
        return R.data(page.setRecords(baseMapper.getEnterpriseWorksheet(enterpriseId, worksheetState, worksheetNo, worksheetName, startTime, endTime, page)));
    }

    @Override
    public R getWorksheetWebDetails(IPage<WorksheetMakerDetailsVO> page, Long worksheetId) {
        WorksheetEntity worksheetEntity = getById(worksheetId);
        if (null == worksheetEntity) {
            return R.fail("没有此工单");
        }
        WorksheetXiaoVO worksheetXiaoVo = BeanUtil.copy(worksheetEntity, WorksheetXiaoVO.class);
        worksheetXiaoVo.setWorksheetId(worksheetId);
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
        map.put("worksheetMakerDetails", worksheetMakerDetails);
        return R.data(map);
    }

    @Override
    public R closeOrOpen(Long worksheetId, Integer variable) {
        WorksheetEntity worksheetEntity = getById(worksheetId);
        if (variable != 1 && variable != 2) {
            return R.fail("参数有误");
        }
        if (1 == variable) {
            worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
            saveOrUpdate(worksheetEntity);
            return R.success("关闭成功");
        } else {
            Integer orderGrabbingCount = worksheetMakerService.getOrderGrabbingCount(worksheetId);
            if (orderGrabbingCount >= worksheetEntity.getUpPersonNum()) {
                return R.fail("开始失败，抢单人数到达上限！！");
            }
            worksheetEntity.setWorksheetState(WorksheetState.PUBLISHING);
            saveOrUpdate(worksheetEntity);
            return R.success("开启成功");
        }
    }

    @Override
    public R kickOut(Long worksheetMakerId) {
        WorksheetMakerEntity worksheetMakerEntity = worksheetMakerService.getById(worksheetMakerId);
        if (null == worksheetMakerEntity) {
            return R.fail("创客没有抢单记录");
        }
        worksheetMakerService.removeById(worksheetMakerEntity);
        return R.success("移除成功");
    }

    @Override
    public R checkAccept(Long worksheetMakerId, BigDecimal checkMoney) {
        WorksheetMakerEntity worksheetMakerEntity = worksheetMakerService.getById(worksheetMakerId);
        WorksheetEntity worksheetEntity = getById(worksheetMakerEntity.getWorksheetId());
        if (!worksheetEntity.getWorksheetState().equals(WorksheetState.CLOSED)) {
            return R.fail("工单还没有关单");
        }
        worksheetMakerEntity.setCheckMoney(checkMoney);
        worksheetMakerEntity.setCheckDate(new Date());
        worksheetMakerEntity.setCheckPerson("商户");
        worksheetMakerService.saveOrUpdate(worksheetMakerEntity);
        return R.success("验收成功");
    }

    @Override
    public R<IPage<EnterpriseWorksheetDetailVO>> getWorksheetDetailsByMaker(Long enterpriseId, Long makerId, IPage<EnterpriseWorksheetDetailVO> page) {
        return R.data(page.setRecords(baseMapper.getWorksheetDetailsByMaker(enterpriseId, makerId, page)));
    }

    @Override
    public R<IPage<WorksheetListVO>> queryWorksheetListByEnterprise(Long enterpriseId, WorksheetFinishedListDTO worksheetFinishedListDTO, IPage<WorksheetListVO> page) {
        return R.data(page.setRecords(baseMapper.queryWorksheetListByEnterprise(enterpriseId, worksheetFinishedListDTO, page)));
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
    public R<WorksheetNoIdVO> getByWorksheetId(Long worksheetId) {

        QueryWrapper<WorksheetEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WorksheetEntity::getId, worksheetId);

        WorksheetEntity worksheetEntity = baseMapper.selectOne(queryWrapper);
        if (worksheetEntity == null) {
            return R.fail("工单不存在");
        }

        return R.data(BeanUtil.copy(worksheetEntity, WorksheetNoIdVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R closeOrOpenList(String worksheetIds, Integer variable) {
        String[] split = worksheetIds.split(",");
        if (variable != 1 && variable != 2) {
            return R.fail("参数有误");
        }
        for (int i = 0; i < split.length; i++) {
            WorksheetEntity worksheetEntity = getById(split[i]);
            if (WorksheetState.CHECKACCEPT.equals(worksheetEntity.getWorksheetState())) {
                return R.fail("验收中的订单，不能删除和重新开启！");
            }
            if (1 == variable) {
                worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
                saveOrUpdate(worksheetEntity);
            } else {
                worksheetEntity.setWorksheetState(WorksheetState.PUBLISHING);
                saveOrUpdate(worksheetEntity);
            }
        }
        return R.success("操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R deleteWorksheetList(String worksheetIds) {
        String[] split = worksheetIds.split(",");
        for (int i = 0; i < split.length; i++) {
            WorksheetEntity worksheetEntity = getById(split[i]);
            removeById(worksheetEntity.getId());
        }
        return R.success("操作成功");
    }

    @Override
    public R wholeWorksheetCheck(Long worksheetId) {
        WorksheetEntity byId = getById(worksheetId);
        if (!WorksheetState.CHECKACCEPT.equals(byId.getWorksheetState())) {
            return R.fail("工单状态不对应");
        }
        List<WorksheetMakerDetailsVO> worksheetMakerDetails = worksheetMakerService.getWorksheetMakerDetails(worksheetId);
        for (WorksheetMakerDetailsVO worksheetMakerDetailsVO : worksheetMakerDetails) {
            if (!(WorksheetMakerState.VALIDATION.equals(worksheetMakerDetailsVO.getWorksheetMakerState()) || WorksheetMakerState.FAILED.equals(worksheetMakerDetailsVO.getWorksheetMakerState()))) {
                return R.fail("请全部验收后再确认验收");
            }
        }


        byId.setWorksheetState(WorksheetState.FINISHED);
        saveOrUpdate(byId);
        return R.success("操作成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized R<String> orderGrabbing(Long worksheetId, Long makerId) {

        WorksheetEntity worksheetEntity = getById(worksheetId);
        if (null == worksheetEntity) {
            return R.fail("工单不存在");
        }

        if (WorksheetMode.DISPATCH.equals(worksheetEntity.getWorksheetMode())) {
            return R.fail("工单不支持抢单");
        }

        MakerEntity makerEntity = iUserClient.queryMakerById(makerId);
        if (null == makerEntity) {
            return R.fail("创客不存在");
        }

        if (StringUtil.isBlank(makerEntity.getIdcardNo())) {
            return R.fail("请上传身份证后重试!!");
        }

        if (!WorksheetState.PUBLISHING.equals(worksheetEntity.getWorksheetState())) {
            return R.fail("非发布中工单，不可抢单");
        }

        int worksheetMakerCount = worksheetMakerService.count(Wrappers.<WorksheetMakerEntity>query().lambda().eq(WorksheetMakerEntity::getWorksheetId, worksheetEntity.getId()));
        if (worksheetMakerCount >= worksheetEntity.getUpPersonNum()) {
            return R.fail("工单已抢完");
        }

        int worksheetMakerNum = worksheetMakerService.count(Wrappers.<WorksheetMakerEntity>query().lambda()
                .eq(WorksheetMakerEntity::getMakerId, makerEntity.getId())
                .eq(WorksheetMakerEntity::getWorksheetId, worksheetEntity.getId()));
        if (worksheetMakerNum > 0) {
            return R.fail("此工单，你已经抢过了");
        }

        int individualBusinessEntities = iUserClient.queryIndividualBusinessNumByMakerId(makerEntity.getId());
        if (individualBusinessEntities <= 0 && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALBUSINESS)) {
            return R.fail("创客身份不符-个体");
        }

        int individualEnterpriseEntities = iUserClient.queryIndividualEnterpriseNumByMakerId(makerEntity.getId());
        if (individualEnterpriseEntities <= 0 && worksheetEntity.getMakerType().equals(MakerType.INDIVIDUALENTERPRISE)) {
            return R.fail("创客身份不符-个独");
        }

        WorksheetMakerEntity worksheetMakerEntity = new WorksheetMakerEntity();
        worksheetMakerEntity.setMakerId(makerEntity.getId());
        worksheetMakerEntity.setWorksheetId(worksheetEntity.getId());
        worksheetMakerEntity.setGetType(GetType.GETGRABBING);
        worksheetMakerEntity.setGetOrderDate(new Date());
        worksheetMakerEntity.setMakerName(makerEntity.getName());
        worksheetMakerService.save(worksheetMakerEntity);

        //商户创客关联
        iUserClient.createMakerToEnterpriseRelevance(worksheetEntity.getEnterpriseId(), makerEntity.getId());

        //判断是否关单
        if (worksheetMakerCount + 1 >= worksheetEntity.getUpPersonNum() && WorksheetState.PUBLISHING.equals(worksheetEntity.getWorksheetState())) {
            worksheetEntity.setWorksheetState(WorksheetState.CLOSED);
            worksheetEntity.setCloseDesc("自动关单");
            worksheetEntity.setCloseWorksheetDate(new Date());
            worksheetEntity.setClosePerson("系统");
            updateById(worksheetEntity);
        }

        return R.success("抢单成功");
    }
}
