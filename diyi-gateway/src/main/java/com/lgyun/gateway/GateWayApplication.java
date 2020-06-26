package com.lgyun.gateway;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 网关项目服务器
 *
 * @author liangfeihu
 * @since 2020/6/2 23:07
 */
@EnableHystrix
@SpringCloudApplication
public class GateWayApplication {
    private static Logger logger = LoggerFactory.getLogger(GateWayApplication.class);

    public static void main(String[] args) {
        logger.info("网关项目服务启动开始");
        BladeApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GateWayApplication.class, args);
        logger.info("网关项目服务启动结束");
    }

}
