package com.lgyun.system.order;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 订单模块服务器
 *
 * @author liangfeihu
 * @since 2020/6/6 23:08
 */
@Slf4j
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class OrderApplication {

    public static void main(String[] args) {
        log.info("订单模块服务启动开始");
        BladeApplication.run(AppConstant.APPLICATION_ORDER_NAME, OrderApplication.class, args);
        log.info("订单模块服务启动结束");
    }

}

