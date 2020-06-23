package com.lgyun.system.user;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户启动器
 *
 * @author liangfeihu
 * @since 2020/6/6 22:03
 */
//@SpringCloudApplication
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class UserApplication {

    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
    }

}
