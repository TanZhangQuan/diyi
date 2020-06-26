package com.lgyun.system.user;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户项目服务器
 *
 * @author liangfeihu
 * @since 2020/6/6 22:03
 */
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class UserApplication {
    private static Logger logger = LoggerFactory.getLogger(UserApplication.class);

    public static void main(String[] args) {
        logger.info("用户项目服务启动开始");
        BladeApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
        logger.info("用户项目服务启动结束");
    }

}
