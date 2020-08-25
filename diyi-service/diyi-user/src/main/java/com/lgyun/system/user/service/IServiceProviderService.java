package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.EnterprisesVO;

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
     * @param keyword
     * @return
     */
    R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(Query query, Long serviceProviderId, String keyword);

}

