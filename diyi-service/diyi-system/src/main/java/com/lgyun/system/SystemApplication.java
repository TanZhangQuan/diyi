package com.lgyun.system;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统模块服务器
 *
 * @author tzq
 * @since 2020/6/6 23:08
 */
@Slf4j
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@EnableDiscoveryClient
@EnableCircuitBreaker
public class SystemApplication {

    public static void main(String[] args) {
        log.info("系统模块服务启动开始");
        BladeApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
        log.info("系统模块服务启动结束");
    }

}

