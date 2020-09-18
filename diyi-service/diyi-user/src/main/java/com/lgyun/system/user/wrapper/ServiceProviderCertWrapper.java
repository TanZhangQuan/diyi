package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.ServiceProviderCertEntity;
import com.lgyun.system.user.vo.ServiceProviderCertVO;

/**
 * 服务商资格信息表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-09-17 10:58:41
 */
public class ServiceProviderCertWrapper extends BaseEntityWrapper<ServiceProviderCertEntity, ServiceProviderCertVO> {

    public static ServiceProviderCertWrapper build() {
        return new ServiceProviderCertWrapper();
    }

    @Override
    public ServiceProviderCertVO entityVO(ServiceProviderCertEntity serviceProviderCert) {
        return BeanUtil.copy(serviceProviderCert, ServiceProviderCertVO.class);
    }

}
