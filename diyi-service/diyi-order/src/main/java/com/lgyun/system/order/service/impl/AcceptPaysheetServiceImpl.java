package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AcceptPaysheetType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AcceptPayListDto;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDto;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.mapper.AcceptPaysheetMapper;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.AcceptPayListVO;
import com.lgyun.system.order.vo.AcceptPaysheetByEnterpriseListVO;
import com.lgyun.system.order.vo.AcceptPaysheetWorksheetVO;
import com.lgyun.system.order.vo.PayEnterpriseMakerDetailListVO;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
@Slf4j
@Service
@AllArgsConstructor
public class AcceptPaysheetServiceImpl extends BaseServiceImpl<AcceptPaysheetMapper, AcceptPaysheetEntity> implements IAcceptPaysheetService {

    private IPayEnterpriseService payEnterpriseService;
    private IWorksheetMakerService worksheetMakerService;
    private IWorksheetService worksheetService;

    @Override
    public R<IPage<AcceptPaysheetByEnterpriseListVO>> getAcceptPaysheetsByEnterprise(IPage<AcceptPaysheetByEnterpriseListVO> page, Long enterpriseId, Long makerId) {
        return R.data(page.setRecords(baseMapper.getAcceptPaysheetsByEnterprise(enterpriseId, makerId, page)));
    }

    @Override
    public R<AcceptPaysheetWorksheetVO> getAcceptPaysheetWorksheet(Long makerId, Long acceptPaysheetId) {
        return R.data(baseMapper.getAcceptPaysheetWorksheet(makerId, acceptPaysheetId));
    }

    @Override
    public R<IPage<EnterprisesIdNameListVO>> getEnterprisesByWorksheet(IPage<EnterprisesIdNameListVO> page, Long makerId) {
        return R.data(page.setRecords(baseMapper.getEnterprisesByWorksheet(makerId, page)));
    }

    @Override
    @Transactional
    public R<String> upload(AcceptPaysheetSaveDto acceptPaysheetSaveDto, Long enterpriseId, String uploadSource, String uploadPerson) {

        //判断支付清单是否存在
        PayEnterpriseEntity payEnterpriseEntity = payEnterpriseService.getById(acceptPaysheetSaveDto.getPayEnterpriseId());
        if (payEnterpriseEntity == null) {
            return R.fail("支付清单不存在");
        }

        if (enterpriseId != null) {
            if (!(payEnterpriseEntity.getEnterpriseId().equals(enterpriseId))) {
                return R.fail("支付清单不属于当前商户");
            }
        }

        //根据支付清单ID, 创客ID查询交付支付验收单
        AcceptPaysheetEntity oldAcceptPaysheetEntity = getAcceptPaysheet(acceptPaysheetSaveDto.getPayEnterpriseId(), acceptPaysheetSaveDto.getMakerId());
        if (oldAcceptPaysheetEntity != null) {
            return R.fail("已存在相同交付支付验收单");
        }

        //保存交付支付验收单
        AcceptPaysheetEntity acceptPaysheetEntity = new AcceptPaysheetEntity();
        if (AcceptPaysheetType.SINGLE.equals(acceptPaysheetSaveDto.getAcceptPaysheetType())) {

            if (acceptPaysheetSaveDto.getMakerId() == null) {
                return R.fail("单个上传交付支付验收单需要选择创客");
            }

            acceptPaysheetEntity.setMakerId(acceptPaysheetSaveDto.getMakerId());
        }

        acceptPaysheetEntity.setServiceTimeStart(acceptPaysheetSaveDto.getServiceTimeStart());
        acceptPaysheetEntity.setServiceTimeEnd(acceptPaysheetSaveDto.getServiceTimeEnd());
        acceptPaysheetEntity.setPayEnterpriseId(acceptPaysheetSaveDto.getPayEnterpriseId());
        acceptPaysheetEntity.setAcceptPaysheetType(acceptPaysheetSaveDto.getAcceptPaysheetType());
        acceptPaysheetEntity.setUploadSource(uploadSource);
        acceptPaysheetEntity.setUploadPerson(uploadPerson);
        acceptPaysheetEntity.setAcceptPaysheetUrl(acceptPaysheetSaveDto.getAcceptPaysheetUrl());

        save(acceptPaysheetEntity);

        return R.success("上传成功");
    }

    @Override
    public R<IPage<AcceptPayListVO>> getAcceptPaySheetsByEnterprise(Long enterpriseId, AcceptPayListDto acceptPayListDto, IPage<AcceptPayListVO> page) {

        if (acceptPayListDto.getBeginDate() != null && acceptPayListDto.getEndDate() != null) {
            if (acceptPayListDto.getBeginDate().after(acceptPayListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getAcceptPaySheetsByEnterprise(enterpriseId, acceptPayListDto, page)));
    }

    @Override
    public R<IPage<PayEnterpriseMakerDetailListVO>> getMakerList(Long acceptPaysheetId, IPage<PayEnterpriseMakerDetailListVO> page) {
        return R.data(page.setRecords(baseMapper.getMakerList(acceptPaysheetId, page)));
    }

    @Override
    public AcceptPaysheetEntity getAcceptPaysheet(Long payEnterpriseId, Long makerId) {

        QueryWrapper<AcceptPaysheetEntity> queryWrapper = new QueryWrapper<>();
        if (makerId != null) {
            queryWrapper.lambda().eq(AcceptPaysheetEntity::getPayEnterpriseId, payEnterpriseId)
                    .eq(AcceptPaysheetEntity::getMakerId, makerId);
        } else {
            queryWrapper.lambda().eq(AcceptPaysheetEntity::getPayEnterpriseId, payEnterpriseId)
                    .isNull(true, AcceptPaysheetEntity::getMakerId);
        }

        return baseMapper.selectOne(queryWrapper);
    }

}
