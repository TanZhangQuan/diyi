package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.EnterprisesVO;
import com.lgyun.system.user.vo.ServiceProvidersVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl extends BaseServiceImpl<ServiceProviderMapper, ServiceProviderEntity> implements IServiceProviderService {

    private IEnterpriseProviderService enterpriseProviderService;

    @Override
    public R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(Query query, Long serviceProviderId) {
        return enterpriseProviderService.getEnterpriseByServiceProvider(Condition.getPage(query.setDescs("create_time")), serviceProviderId);
    }

    @Override
    public R<IPage<ServiceProvidersVO>> getServiceProvidersByEnterpriseId(Long payEnterpriseId, IPage<ServiceProvidersVO> page) {
        return R.data(page.setRecords(baseMapper.getServiceProvidersByEnterpriseId(payEnterpriseId, page)));
    }
}
