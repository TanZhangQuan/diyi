package com.lgyun.system.order.feign;

import com.lgyun.common.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;

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

}
