package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;

/**
 * 服务商员工表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-13 17:05:17
 */
public class ServiceProviderWorkerWrapper extends BaseEntityWrapper<ServiceProviderWorkerEntity, ServiceProviderWorkerVO> {

    public static ServiceProviderWorkerWrapper build() {
        return new ServiceProviderWorkerWrapper();
    }

    @Override
    public ServiceProviderWorkerVO entityVO(ServiceProviderWorkerEntity serviceProviderWorker) {
        return BeanUtil.copy(serviceProviderWorker, ServiceProviderWorkerVO.class);
    }

}
