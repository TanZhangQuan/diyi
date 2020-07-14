package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.system.user.entity.*;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    UserInfo userInfo(@RequestParam("userId") Long userId, @RequestParam("userType") UserType userType);

    /**
     * 获取用户信息
     *
     * @param phone 用户手机号
     * @return
     */
    @GetMapping(API_PREFIX + "/phone")
    UserInfo userInfoByPhone(@RequestParam("phone") String phone, @RequestParam("userType") UserType userType);

    /**
     * 获取用户信息
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    @GetMapping(API_PREFIX + "/user-info")
    UserInfo userInfo(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("userType") UserType userType);

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return
     */
    @GetMapping(API_PREFIX + "/user-find-by-id")
    User userFindById(@RequestParam("id") Long id);

    /**
     * 获取创客信息
     *
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-find-by-phone-pwd")
    MakerEntity makerFindByPhoneNumberAndLoginPwd(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("loginPwd") String loginPwd);

    /**
     * 获取创客信息
     *
     * @param phone 创客phone
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-find-by-phone")
    MakerEntity makerFindByPhone(@RequestParam("phone") String phone);

    /**
     * 微信授权登陆
     *
     * @param
     * @return
     */
    @PostMapping(API_PREFIX + "/wechat-authorization")
    R makerSaveOrUpdate(@RequestParam("openid") String openid, @RequestParam("sessionKey") String sessionKey, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("loginPwd") String loginPwd, @RequestParam("grantType") GrantType grantType);


	/**
	 * 获取个独信息
	 */
	@GetMapping(API_PREFIX + "/individualEnterprise/find_by_maker_id")
	IndividualEnterpriseEntity individualEnterpriseFindByMakerId(@RequestParam("makerId") Long makerId);

	/**
	 * 获取个体信息
	 */
	@GetMapping(API_PREFIX + "/individualBusiness/find_by_maker_id")
	IndividualBusinessEntity individualBusinessByMakerId(@RequestParam("makerId") Long makerId);
}
