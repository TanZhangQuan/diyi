package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.ServiceProviderVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
public class ServiceProviderWrapper extends BaseEntityWrapper<ServiceProviderEntity, ServiceProviderVO> {

    public static ServiceProviderWrapper build() {
        return new ServiceProviderWrapper();
    }

    @Override
    public ServiceProviderVO entityVO(ServiceProviderEntity serviceProvider) {

        if (serviceProvider == null){
            return null;
        }

        return BeanUtil.copy(serviceProvider, ServiceProviderVO.class);
    }

}
