package com.lgyun.system.user;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户项目服务器
 *
 * @author tzq
 * @since 2020/6/6 22:03
 */
@Slf4j
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
public class UserApplication {

    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
    }

}
