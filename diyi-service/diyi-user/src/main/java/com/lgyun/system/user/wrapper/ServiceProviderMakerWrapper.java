package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
import com.lgyun.system.user.vo.ServiceProviderMakerVO;

/**
 * 服务商创客关联表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-19 16:01:29
 */
public class ServiceProviderMakerWrapper extends BaseEntityWrapper<ServiceProviderMakerEntity, ServiceProviderMakerVO> {

    public static ServiceProviderMakerWrapper build() {
        return new ServiceProviderMakerWrapper();
    }

    @Override
    public ServiceProviderMakerVO entityVO(ServiceProviderMakerEntity serviceProviderMaker) {
        return BeanUtil.copy(serviceProviderMaker, ServiceProviderMakerVO.class);
    }

}
