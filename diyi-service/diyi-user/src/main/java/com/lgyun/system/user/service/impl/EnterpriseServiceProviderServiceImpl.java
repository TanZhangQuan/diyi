package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.EnterpriseServiceProviderMapper;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

    @Autowired
    @Lazy
    private IServiceProviderService serviceProviderService;

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> getServiceProviderByEnterpriseId(IPage<ServiceProviderIdNameListVO> page, Long enterpriseId, String serviceProviderName) {
        return R.data(page.setRecords(baseMapper.getServiceProviderByEnterpriseId(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public EnterpriseServiceProviderEntity findByEnterpriseIdServiceProviderId(Long enterpriseId, Long serviceProviderId) {
        QueryWrapper<EnterpriseServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseServiceProviderEntity::getEnterpriseId, enterpriseId).
                eq(EnterpriseServiceProviderEntity::getServiceProviderId, serviceProviderId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(IPage<EnterprisesVO> page, Long serviceProviderId, String keyword) {
        return R.data(page.setRecords(baseMapper.getEnterpriseByServiceProvider(serviceProviderId, keyword, page)));
    }

    @Override
    public R<IPage<ServiceProvidersVO>> getServiceProvidersByEnterpriseId(Long enterpriseId, String keyWord, IPage<ServiceProvidersVO> page) {
        return R.data(page.setRecords(baseMapper.getServiceProvidersByEnterpriseId(enterpriseId, keyWord, page)));
    }

    @Override
    public R<IPage<EnterprisesByProviderVO>> getEnterprtisesByServiceProviderId(Long serviceProviderId, String keyWord, IPage<EnterprisesByProviderVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterprtisesByServiceProviderId(serviceProviderId, keyWord, page)));
    }

    @Override
    public R<IPage<EnterpriseIdNameListVO>> getEnterprisesByServiceProvider(IPage<EnterpriseIdNameListVO> page, Long serviceProviderId) {
        return R.data(page.setRecords(baseMapper.getEnterprisesByServiceProvider(serviceProviderId, page)));
    }

    @Override
    public R<String> relevanceEnterpriseServiceProvider(Long enterpriseId, List<Long> serviceProviderIdList, AdminEntity adminEntity) {

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        for (Long serviceProviderId : serviceProviderIdList) {

            ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
            if (serviceProviderEntity == null) {
                log.error("服务商不存在");
                continue;
            }

            EnterpriseServiceProviderEntity enterpriseServiceProviderEntity = findByEnterpriseIdServiceProviderId(enterpriseId, serviceProviderId);
            if (enterpriseServiceProviderEntity == null) {
                enterpriseServiceProviderEntity = new EnterpriseServiceProviderEntity();
                enterpriseServiceProviderEntity.setEnterpriseId(enterpriseId);
                enterpriseServiceProviderEntity.setServiceProviderId(serviceProviderId);
                enterpriseServiceProviderEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                enterpriseServiceProviderEntity.setMatchPerson(adminEntity.getName());
                enterpriseServiceProviderEntity.setMatchDesc("平台匹配服务商给商户");
                save(enterpriseServiceProviderEntity);
            } else {
                if (CooperateStatus.COOPERATESTOP.equals(enterpriseServiceProviderEntity.getCooperateStatus())) {
                    enterpriseServiceProviderEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                    updateById(enterpriseServiceProviderEntity);
                }
            }
        }

        return R.success("商户匹配服务商成功");
    }

}
