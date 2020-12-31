package com.lgyun.auth;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户认证服务器
 *
 * @author tzq
 * @since 2020/6/6 00:15
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
public class AuthApplication {

    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
    }

}
