package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.system.user.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME,
	fallback = IUserClientFallback.class
)
public interface IUserClient {

	String API_PREFIX = "/user";

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info-by-id")
	R<UserInfo> userInfo(@RequestParam("userId") Long userId);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account    账号
	 * @param password   密码
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info")
	R<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account, @RequestParam("password") String password);

}
