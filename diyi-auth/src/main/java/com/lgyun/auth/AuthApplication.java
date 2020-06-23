package com.lgyun.auth;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户认证服务器
 *
 * @author liangfeihu
 * @since 2020/6/6 00:15
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class AuthApplication {

	public static void main(String[] args) {
		BladeApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
	}

}
