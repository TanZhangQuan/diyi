package com.lgyun.auth;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户认证服务器
 *
 * @author liangfeihu
 * @since 2020/6/6 00:15
 */
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class AuthApplication {
    private static Logger logger = LoggerFactory.getLogger(AuthApplication.class);

    public static void main(String[] args) {
        logger.info("用户认证服务启动开始");
        BladeApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
        logger.info("用户认证服务启动结束");
    }

}
