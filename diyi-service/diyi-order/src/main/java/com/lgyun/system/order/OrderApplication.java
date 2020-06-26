package com.lgyun.system.order;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统模块启动器
 *
 * @author liangfeihu
 * @since 2020/6/6 23:08
 */
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class OrderApplication {

    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_ORDER_NAME, OrderApplication.class, args);
    }

}

