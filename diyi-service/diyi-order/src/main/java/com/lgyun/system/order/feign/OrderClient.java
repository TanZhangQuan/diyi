package com.lgyun.system.order.feign;

import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.service.IEnterpriseProviderInvoiceCatalogsService;
import com.lgyun.system.order.service.IServiceProviderInvoiceCatalogsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单服务Feign实现类
 *
 * @author tzq
 * @since 2020/6/6 22:11
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class OrderClient implements IOrderClient {

    private IAddressService addressService;
    private IServiceProviderInvoiceCatalogsService serviceProviderInvoiceCatalogsService;
    private IEnterpriseProviderInvoiceCatalogsService enterpriseProviderInvoiceCatalogsService;

    @Override
    public void createAddress(AddressEntity addressEntity) {
        addressService.save(addressEntity);
    }

    @Override
    public void createServiceProviderInvoiceCatalogs(ServiceProviderInvoiceCatalogsEntity serviceProviderInvoiceCatalogsEntity) {
        serviceProviderInvoiceCatalogsService.save(serviceProviderInvoiceCatalogsEntity);
    }

    @Override
    public void createEnterpriseProviderInvoiceCatalogs(EnterpriseProviderInvoiceCatalogsEntity enterpriseProviderInvoiceCatalogsEntity) {
        enterpriseProviderInvoiceCatalogsService.save(enterpriseProviderInvoiceCatalogsEntity);
    }
}
