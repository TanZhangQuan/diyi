package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.enumeration.ServiceProviderMakerRelType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
import com.lgyun.system.user.mapper.ServiceProviderMakerMapper;
import com.lgyun.system.user.service.IServiceProviderMakerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 服务商创客关联表 Service 实现
 *
 * @author tzq
 * @since 2020-08-19 16:01:29
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderMakerServiceImpl extends BaseServiceImpl<ServiceProviderMakerMapper, ServiceProviderMakerEntity> implements IServiceProviderMakerService {

    @Override
    public void associatedServiceProviderMaker(Long enterpriseId, Long serviceProviderId, Long makerId, ServiceProviderMakerRelType relType) {

        QueryWrapper<ServiceProviderMakerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderMakerEntity::getEnterpriseId, enterpriseId)
                .eq(ServiceProviderMakerEntity::getServiceProviderId, serviceProviderId)
                .eq(ServiceProviderMakerEntity::getMakerId, makerId);

        ServiceProviderMakerEntity serviceProviderMaker = baseMapper.selectOne(queryWrapper);

        if (null == serviceProviderMaker) {
            serviceProviderMaker = new ServiceProviderMakerEntity();
            serviceProviderMaker.setEnterpriseId(enterpriseId);
            serviceProviderMaker.setServiceProviderId(serviceProviderId);
            serviceProviderMaker.setMakerId(makerId);
            serviceProviderMaker.setRelType(relType);
            save(serviceProviderMaker);

        }
    }
}
