package com.lgyun.gateway;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 网关项目服务器
 *
 * @author liangfeihu
 * @since 2020/6/2 23:07
 */
@Slf4j
@EnableHystrix
@SpringCloudApplication
public class GateWayApplication {

    public static void main(String[] args) {
        log.info("网关项目服务启动开始");
        BladeApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GateWayApplication.class, args);
        log.info("网关项目服务启动结束");
    }

}
