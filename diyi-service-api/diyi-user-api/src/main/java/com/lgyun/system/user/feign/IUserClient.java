package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.core.mp.support.Query;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.system.user.dto.IndividualBusinessListByMakerDto;
import com.lgyun.system.user.dto.IndividualEnterpriseListByMakerDto;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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

    /**
     * 根据创客Id
     */
    @GetMapping(API_PREFIX + "/runCompany/find_by_maker_id")
    R findRunCompanyMakerId(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("makerId") Long makerId);

    /**
     * 新增平台运营公司（平台方）信息ID
     */
    @PostMapping(API_PREFIX + "/runCompanySave")
    R runCompanySave(@Valid @RequestBody RunCompanyDto runCompanyDto);

    /**
     * 根据姓名分页
     *
     * @return
     */
    @GetMapping(API_PREFIX + "/findMakerNamePage")
    R findMakerNamePage(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("name") String name);

    //查询当前创客的所有个独
    @PostMapping(API_PREFIX + "/individualEnterprise/listByMaker")
    R listByMaker(@Valid @RequestBody IndividualEnterpriseListByMakerDto individualEnterpriseListByMakerDto);

    //查询当前创客的所有个体户
    @PostMapping(API_PREFIX + "/individualBusiness/listByMaker")
    R listByMaker(@Valid @RequestBody IndividualBusinessListByMakerDto individualBusinessListByMakerDto);

    /**
     * 根据Id获取个独信息
     */
    @GetMapping(API_PREFIX + "/individualEnterprise/find_by_id")
    IndividualEnterpriseEntity individualEnterpriseFindById(@RequestParam("individualEnterpriseId") Long individualEnterpriseId);

    /**
     * 根据Id获取个体信息
     */
    @GetMapping(API_PREFIX + "/individualBusiness/find_by_id")
    IndividualBusinessEntity individualBusinessById(@RequestParam("individualBusinessId") Long individualBusinessId);

}
