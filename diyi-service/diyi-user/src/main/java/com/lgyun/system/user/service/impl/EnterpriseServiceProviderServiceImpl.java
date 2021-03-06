package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.constant.CustomConstant;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.InvoiceDemand;
import com.lgyun.common.enumeration.SetType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseServiceProviderEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.EnterpriseServiceProviderMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;
import com.lgyun.system.user.vo.EnterprisesVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseServiceProviderServiceImpl extends BaseServiceImpl<EnterpriseServiceProviderMapper, EnterpriseServiceProviderEntity> implements IEnterpriseServiceProviderService {

    private final IOrderClient orderClient;

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

    @Autowired
    @Lazy
    private IServiceProviderService serviceProviderService;

    @Override
    public int queryCountByEnterpriseIdAndServiceProviderId(Long enterpriseId, Long serviceProviderId, CooperateStatus cooperateStatus) {
        QueryWrapper<EnterpriseServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseServiceProviderEntity::getEnterpriseId, enterpriseId)
                .eq(EnterpriseServiceProviderEntity::getServiceProviderId, serviceProviderId)
                .eq(EnterpriseServiceProviderEntity::getCooperateStatus, cooperateStatus);

        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public EnterpriseServiceProviderEntity findByEnterpriseAndServiceProvider(Long enterpriseId, Long serviceProviderId) {
        QueryWrapper<EnterpriseServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseServiceProviderEntity::getEnterpriseId, enterpriseId).
                eq(EnterpriseServiceProviderEntity::getServiceProviderId, serviceProviderId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<EnterprisesVO>> queryRelevanceEnterpriseList(Long serviceProviderId, String keyword, IPage<EnterprisesVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelevanceEnterpriseList(serviceProviderId, keyword, page)));
    }

    @Override
    public R<IPage<CooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long enterpriseId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationServiceProviderList(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R<IPage<CooperationEnterprisesListVO>> queryCooperationEnterpriseList(Long serviceProviderId, String enterpriseName, IPage<CooperationEnterprisesListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationEnterpriseList(serviceProviderId, enterpriseName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> relevanceEnterpriseServiceProvider(Long enterpriseId, Long serviceProviderId, String matchDesc) {

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        EnterpriseServiceProviderEntity enterpriseServiceProviderEntity = findByEnterpriseAndServiceProvider(enterpriseId, serviceProviderId);
        if (enterpriseServiceProviderEntity == null) {
            enterpriseServiceProviderEntity = new EnterpriseServiceProviderEntity();
            enterpriseServiceProviderEntity.setEnterpriseId(enterpriseId);
            enterpriseServiceProviderEntity.setServiceProviderId(serviceProviderId);
            enterpriseServiceProviderEntity.setMatchDesc(matchDesc);
            save(enterpriseServiceProviderEntity);

            //创建默认开票类目：平台服务费
            EnterpriseProviderInvoiceCatalogsEntity platformEnterpriseProviderInvoiceCatalogs = new EnterpriseProviderInvoiceCatalogsEntity();
            platformEnterpriseProviderInvoiceCatalogs.setEnterpriseId(enterpriseId);
            platformEnterpriseProviderInvoiceCatalogs.setServiceProviderId(serviceProviderId);
            platformEnterpriseProviderInvoiceCatalogs.setInvoiceCatalogName(CustomConstant.PLATFORM_SERVICE_FEE);
            platformEnterpriseProviderInvoiceCatalogs.setInvoiceDemand(InvoiceDemand.ACCUMULATION);
            platformEnterpriseProviderInvoiceCatalogs.setSetType(SetType.SYSTEM);
            orderClient.createEnterpriseProviderInvoiceCatalogs(platformEnterpriseProviderInvoiceCatalogs);

            //创建默认开票类目：服务费
            EnterpriseProviderInvoiceCatalogsEntity serviceEnterpriseProviderInvoiceCatalogs = new EnterpriseProviderInvoiceCatalogsEntity();
            serviceEnterpriseProviderInvoiceCatalogs.setEnterpriseId(enterpriseId);
            serviceEnterpriseProviderInvoiceCatalogs.setServiceProviderId(serviceProviderId);
            serviceEnterpriseProviderInvoiceCatalogs.setInvoiceCatalogName(CustomConstant.SERVICE_FEE);
            serviceEnterpriseProviderInvoiceCatalogs.setInvoiceDemand(InvoiceDemand.ACCUMULATION);
            serviceEnterpriseProviderInvoiceCatalogs.setSetType(SetType.SYSTEM);
            orderClient.createEnterpriseProviderInvoiceCatalogs(serviceEnterpriseProviderInvoiceCatalogs);

        } else {
            if (!(CooperateStatus.COOPERATING.equals(enterpriseServiceProviderEntity.getCooperateStatus()))) {
                enterpriseServiceProviderEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                updateById(enterpriseServiceProviderEntity);
            }
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateCooperationStatus(Long enterpriseId, Long serviceProviderId, CooperateStatus cooperateStatus) {

        int enterpriseNum = enterpriseService.queryCountById(enterpriseId);
        if (enterpriseNum <= 0) {
            return R.fail("商户不存在");
        }

        int serviceProviderNum = serviceProviderService.queryCountById(serviceProviderId);
        if (serviceProviderNum <= 0) {
            return R.fail("服务商不存在");
        }

        EnterpriseServiceProviderEntity enterpriseServiceProviderEntity = findByEnterpriseAndServiceProvider(enterpriseId, serviceProviderId);
        if (enterpriseServiceProviderEntity == null) {
            return R.fail("商户服务商不存在合作关系");
        }

        if (!(enterpriseServiceProviderEntity.getCooperateStatus().equals(cooperateStatus))) {
            enterpriseServiceProviderEntity.setCooperateStatus(cooperateStatus);
            updateById(enterpriseServiceProviderEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
