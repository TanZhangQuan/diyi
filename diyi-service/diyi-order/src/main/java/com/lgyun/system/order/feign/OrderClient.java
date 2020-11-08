package com.lgyun.system.order.feign;

import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.service.IAddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单服务Feign实现类
 *
 * @author liangfeihu
 * @since 2020/6/6 22:11
 */
@RestController
@AllArgsConstructor
public class OrderClient implements IOrderClient {

    private IAddressService addressService;

    @Override
    public void createAddress(AddressEntity addressEntity) {
        addressService.save(addressEntity);
    }
}
