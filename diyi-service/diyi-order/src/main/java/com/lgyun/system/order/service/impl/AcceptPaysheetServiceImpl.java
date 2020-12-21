package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.AcceptPaysheetType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AcceptPaysheetSaveDTO;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.entity.AcceptPaysheetListEntity;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.mapper.AcceptPaysheetMapper;
import com.lgyun.system.order.service.IAcceptPaysheetListService;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.vo.AcceptPaysheetAndCsListMakerVO;
import com.lgyun.system.order.vo.AcceptPaysheetDetailEnterpriseVO;
import com.lgyun.system.order.vo.AcceptPaysheetDetailMakerVO;
import com.lgyun.system.order.vo.AcceptPaysheetListEnterpriseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-17 14:38:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcceptPaysheetServiceImpl extends BaseServiceImpl<AcceptPaysheetMapper, AcceptPaysheetEntity> implements IAcceptPaysheetService {

    private final IAcceptPaysheetListService acceptPaysheetPayListService;

    @Autowired
    @Lazy
    private IPayEnterpriseService payEnterpriseService;

    @Override
    public R<IPage<AcceptPaysheetAndCsListMakerVO>> queryTotalSubAcceptPaysheetListMaker(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsListMakerVO> page) {
        return R.data(page.setRecords(baseMapper.queryTotalSubAcceptPaysheetListMaker(enterpriseId, makerId, page)));
    }

    @Override
    public R<AcceptPaysheetDetailMakerVO> queryTotalSubAcceptPaysheetDetailMaker(Long makerId, Long acceptPaysheetId) {
        return R.data(baseMapper.queryTotalSubAcceptPaysheetDetailMaker(makerId, acceptPaysheetId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> uploadAcceptPaysheet(Long enterpriseId, Long serviceProviderId, AcceptPaysheetSaveDTO acceptPaysheetSaveDto) {

        //判断支付清单是否存在
        PayEnterpriseEntity payEnterpriseEntity = payEnterpriseService.getById(acceptPaysheetSaveDto.getPayEnterpriseId());
        if (payEnterpriseEntity == null) {
            return R.fail("总包支付清单不存在");
        }

        if (enterpriseId != null) {
            if (!(payEnterpriseEntity.getEnterpriseId().equals(enterpriseId))) {
                return R.fail("总包支付清单不属于当前商户");
            }
        }

        if (serviceProviderId != null) {
            if (!(payEnterpriseEntity.getServiceProviderId().equals(serviceProviderId))) {
                return R.fail("总包支付清单不属于当前服务商");
            }
        }

        //保存交付支付验收单
        if (AcceptPaysheetType.SINGLE.equals(acceptPaysheetSaveDto.getAcceptPaysheetType())) {
            if (acceptPaysheetSaveDto.getPayMakerId() == null) {
                return R.fail("请选择分包");
            }
        } else {
            if (acceptPaysheetSaveDto.getPayMakerIdList() != null && acceptPaysheetSaveDto.getPayMakerIdList().isEmpty()) {
                return R.fail("请选择分包");
            }

            if (acceptPaysheetSaveDto.getPayMakerIdList().size() < 2) {
                return R.fail("请选择两个以上分包");
            }
        }

        //判断分包支付明细是否已开交付支付验收单
        if (AcceptPaysheetType.SINGLE.equals(acceptPaysheetSaveDto.getAcceptPaysheetType())) {
            if (isAcceptPaysheet(acceptPaysheetSaveDto.getPayMakerId())) {
                return R.fail("存在已上传交付支付验收单的分包支付明细");
            }
        } else {
            for (Long payMakerId : acceptPaysheetSaveDto.getPayMakerIdList()) {
                if (isAcceptPaysheet(payMakerId)) {
                    return R.fail("存在已上传交付支付验收单的分包支付明细");
                }
            }
        }

        AcceptPaysheetEntity acceptPaysheetEntity = new AcceptPaysheetEntity();
        BeanUtils.copyProperties(acceptPaysheetSaveDto, acceptPaysheetEntity);
        save(acceptPaysheetEntity);

        if (AcceptPaysheetType.MANY.equals(acceptPaysheetSaveDto.getAcceptPaysheetType())) {
            acceptPaysheetSaveDto.getPayMakerIdList().forEach(payMakerId -> {
                AcceptPaysheetListEntity acceptPaysheetListEntity = new AcceptPaysheetListEntity();
                acceptPaysheetListEntity.setAcceptPaysheetId(acceptPaysheetEntity.getId());
                acceptPaysheetListEntity.setPayMakerId(payMakerId);
                acceptPaysheetPayListService.save(acceptPaysheetListEntity);
            });
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<IPage<AcceptPaysheetListEnterpriseVO>> queryTotalSubAcceptPaysheetListEnterprise(Long enterpriseId, AcceptSheetAndCsListDTO acceptSheetAndCsListDto, IPage<AcceptPaysheetListEnterpriseVO> page) {

        if (acceptSheetAndCsListDto.getBeginDate() != null && acceptSheetAndCsListDto.getEndDate() != null) {
            if (acceptSheetAndCsListDto.getBeginDate().after(acceptSheetAndCsListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryTotalSubAcceptPaysheetListEnterprise(enterpriseId, acceptSheetAndCsListDto, page)));
    }

    @Override
    public R<AcceptPaysheetDetailEnterpriseVO> queryTotalSubAcceptPaysheetDetailEnterprise(Long acceptPaysheetId) {
        return R.data(baseMapper.queryTotalSubAcceptPaysheetDetailEnterprise(acceptPaysheetId));
    }

    @Override
    public boolean isAcceptPaysheet(Long payMakerId) {

        int acceptPaysheetNum = count(Wrappers.<AcceptPaysheetEntity>query().lambda().eq(AcceptPaysheetEntity::getPayMakerId, payMakerId));
        int acceptPaysheetListNum = acceptPaysheetPayListService.count(Wrappers.<AcceptPaysheetListEntity>query().lambda().eq(AcceptPaysheetListEntity::getPayMakerId, payMakerId));

        return (acceptPaysheetNum + acceptPaysheetListNum) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAcceptPaysheet(Long payEnterpriseId) {

        List<Long> acceptPaysheetIdList = baseMapper.queryAcceptPaysheetIdList(payEnterpriseId);

        //删除交付支付验收单记录
        baseMapper.deleteAcceptPaysheet(payEnterpriseId);

        //删除清单式子表记录
        if (acceptPaysheetIdList != null && !acceptPaysheetIdList.isEmpty()) {
            acceptPaysheetIdList.forEach(acceptPaysheetPayListService::deleteAcceptPaysheetList);
        }

    }

}
