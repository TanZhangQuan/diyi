package com.lgyun.desk;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Desk 服务启动器
 *
 * @author liangfeihu
 * @since 2020/6/30 11:21
 */
@Slf4j
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
public class DeskApplication {

	public static void main(String[] args) {
		BladeApplication.run(AppConstant.APPLICATION_DESK_NAME, DeskApplication.class, args);
	}

}




