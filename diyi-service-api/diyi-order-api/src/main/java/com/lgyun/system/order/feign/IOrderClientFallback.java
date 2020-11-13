package com.lgyun.system.order.feign;

import com.lgyun.common.exception.CustomException;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author tzq
 * @since 2020/6/6 00:29
 */
@Component
public class IOrderClientFallback implements IOrderClient {

    @Override
    public void createAddress(AddressEntity addressEntity) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public void createServiceProviderInvoiceCatalogs(ServiceProviderInvoiceCatalogsEntity serviceProviderInvoiceCatalogsEntity) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public void createEnterpriseProviderInvoiceCatalogs(EnterpriseProviderInvoiceCatalogsEntity enterpriseProviderInvoiceCatalogsEntity) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }
}
