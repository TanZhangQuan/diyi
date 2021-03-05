package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ServiceProviderMakerRelType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;

/**
 * 服务商创客关联表 Service 接口
 *
 * @author tzq
 * @since 2020-08-19 16:01:29
 */
public interface IServiceProviderMakerService extends BaseService<ServiceProviderMakerEntity> {

    /**
     * 服务商和创客关联
     */
    void associatedServiceProviderMaker(Long enterpriseId, Long serviceProviderId, Long makerId, ServiceProviderMakerRelType relType);


    /**
     *查询合作服务商
     */
    R queryCooperationServiceProviderList(Long makerId);
}

