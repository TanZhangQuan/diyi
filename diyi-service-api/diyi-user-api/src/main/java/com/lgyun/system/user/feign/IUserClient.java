package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

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
     * @param userId
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/user-info-by-id")
    UserInfo userInfo(@RequestParam("userId") Long userId, @RequestParam("userType") UserType userType);

    /**
     * 获取用户信息
     *
     * @param phone
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/phone")
    UserInfo userInfoByPhone(@RequestParam("phone") String phone, @RequestParam("userType") UserType userType);

    /**
     * 获取用户信息
     *
     * @param account
     * @param password
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/user-info")
    UserInfo userInfo(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("userType") UserType userType);

    /**
     * 获取创客信息
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-find-by-phone-number")
    MakerEntity makerFindByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 获取商户员工信息
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/enterprise-worker-find-by-phone-number")
    EnterpriseWorkerEntity enterpriseWorkerFindByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 创客处理
     *
     * @param openid
     * @param sessionKey
     * @param phoneNumber
     * @param loginPwd
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/maker-deal")
    R<String> makerDeal(@RequestParam("openid") String openid, @RequestParam("sessionKey") String sessionKey, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("loginPwd") String loginPwd, @RequestParam("grantType") GrantType grantType);

    /**
     * 商户处理
     *
     * @param phoneNumber
     * @param loginPwd
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/enterprise-worker-deal")
    R<String> enterpriseWorkerDeal(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("loginPwd") String loginPwd, @RequestParam("grantType") GrantType grantType);

    /**
     * 获取个独信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualEnterprise/find_by_maker_id")
    List<IndividualEnterpriseEntity> individualEnterpriseFindByMakerId(@RequestParam("makerId") Long makerId);

    /**
     * 获取个体信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualBusiness/find_by_maker_id")
    List<IndividualBusinessEntity> individualBusinessByMakerId(@RequestParam("makerId") Long makerId);

    /**
     * 根据创客Id
     *
     * @param current
     * @param size
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/runCompany/find_by_maker_id")
    R<IPage<RunCompanyEntity>> findRunCompanyMakerId(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("makerId") Long makerId);

    /**
     * 新增平台运营公司（平台方）信息ID
     *
     * @param runCompanyDto
     * @return
     */
    @PostMapping(API_PREFIX + "/runCompanySave")
    R<String> runCompanySave(@Valid @RequestBody RunCompanyDto runCompanyDto);

    /**
     * 查询当前创客的所有个独
     *
     * @param current
     * @param size
     * @param makerId
     * @param ibstate
     * @return
     */
    @PostMapping(API_PREFIX + "/individualEnterprise/listByMaker")
    R<IPage<IndividualBusinessEnterpriseListByMakerVO>> individualEnterpriseListByMaker(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("makerId") Long makerId, @RequestParam("ibstate") Ibstate ibstate);

    /**
     * 查询当前创客的所有个体户
     *
     * @param current
     * @param size
     * @param makerId
     * @param ibstate
     * @return
     */
    @PostMapping(API_PREFIX + "/individualBusiness/listByMaker")
    R<IPage<IndividualBusinessEnterpriseListByMakerVO>> individualBusinessListByMaker(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("makerId") Long makerId, @RequestParam("ibstate") Ibstate ibstate);

    /**
     * 根据Id获取个独信息
     *
     * @param individualEnterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualEnterprise/find_by_id")
    IndividualEnterpriseEntity individualEnterpriseFindById(@RequestParam("individualEnterpriseId") Long individualEnterpriseId);

    /**
     * 根据Id获取个体信息
     *
     * @param individualBusinessId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualBusiness/find_by_id")
    IndividualBusinessEntity individualBusinessById(@RequestParam("individualBusinessId") Long individualBusinessId);

    /**
     * 根据商户id查询商户
     *
     * @param enterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/enterprise/get_by_id")
    EnterpriseEntity getEnterpriseById(@RequestParam("enterpriseId") Long enterpriseId);

    /**
     * 根据创客id获取创客信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-find-by-id")
    MakerEntity makerFindById(@RequestParam("makerId") Long makerId);

    /**
     * 根据user_id获取创客
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-maker")
    MakerEntity currentMaker(@RequestBody BladeUser bladeUser);

    /**
     * 根据创客姓名分页查询
     */
    @PostMapping(API_PREFIX + "/maker/getMakerName")
    R getMakerName(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam(name = "makerName",required = false) String makerName);

    /**
     * 根据user_id获取商户
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-enterprise")
    EnterpriseEntity currentEnterprise(@RequestBody BladeUser bladeUser);

}
