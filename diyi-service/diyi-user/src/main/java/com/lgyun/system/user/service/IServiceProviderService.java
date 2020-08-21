package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.EnterprisesVO;
import com.lgyun.system.user.vo.ServiceProvidersVO;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
public interface IServiceProviderService extends BaseService<ServiceProviderEntity> {

    /**
     * 获取服务商关联的所有商户
     *
     * @param query
     * @param serviceProviderId
     * @return
     */
    R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(Query query, Long serviceProviderId);

    /**
     * 获取当前商户合作服务商
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    R<IPage<ServiceProvidersVO>> getServiceProvidersByEnterpriseId(Long payEnterpriseId, IPage<ServiceProvidersVO> page);
}

