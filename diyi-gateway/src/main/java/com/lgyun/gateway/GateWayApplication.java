package com.lgyun.gateway;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 网关项目启动类
 *
 * @author liangfeihu
 * @since 2020/6/2 23:07
 */
@EnableHystrix
@SpringCloudApplication
public class GateWayApplication {

    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GateWayApplication.class, args);
    }

}
