package com.lgyun.system.order.feign;

import com.lgyun.common.constant.AppConstant;
import com.lgyun.system.order.entity.AddressEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Order Feign接口类
 *
 * @author tzq
 */
@FeignClient(
        value = AppConstant.APPLICATION_ORDER_NAME,
        fallback = IOrderClientFallback.class
)
public interface IOrderClient {

    String API_PREFIX = "/order";

    /**
     * 新建收货地址
     *
     * @param addressEntity
     */
    @PostMapping(API_PREFIX + "/create-address")
    void createAddress(@RequestBody AddressEntity addressEntity);
}
