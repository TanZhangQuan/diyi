package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
     * 查询用户信息
     *
     * @param userId
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/query-user-info-by-user-id")
    UserInfo queryUserInfoByUserId(@RequestParam("userId") Long userId, @RequestParam("userType") UserType userType);

    /**
     * 查询用户信息
     *
     * @param phone
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/query-user-info-by-phone")
    UserInfo queryUserInfoByPhone(@RequestParam("phone") String phone, @RequestParam("userType") UserType userType);

    /**
     * 查询用户信息
     *
     * @param account
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/query-user-info-by-account")
    UserInfo queryUserInfoByAccount(@RequestParam("account") String account, @RequestParam("userType") UserType userType);

    /**
     * 查询当前管理员
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-admin")
    R<AdminEntity> currentAdmin(@RequestBody BladeUser bladeUser);

    /**
     * 查询当前创客
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-maker")
    R<MakerEntity> currentMaker(@RequestBody BladeUser bladeUser);

    /**
     * 查询当前商户
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-enterprise-worker")
    R<EnterpriseWorkerEntity> currentEnterpriseWorker(@RequestBody BladeUser bladeUser);

    /**
     * 查询当前服务商
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-service-provider-worker")
    R<ServiceProviderWorkerEntity> currentServiceProviderWorker(@RequestBody BladeUser bladeUser);

    /**
     * 根据创客支付明细查询创客名称
     *
     * @param payMakerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-name")
    String queryMakerName(@RequestParam("payMakerId") Long payMakerId);

    /**
     * 根据创客id查询创客信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-by-id")
    MakerEntity queryMakerById(@RequestParam("makerId") Long makerId);

    /**
     * 根据身份证号码查询创客信息
     *
     * @param idcardNo
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-by-idcard-no")
    MakerEntity queryMakerByIdcardNo(@RequestParam("idcardNo") String idcardNo);

    /**
     * 查询创客信息
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-by-phone-number")
    MakerEntity queryMakerByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询管理员数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-admin-count-by-phone-number")
    Integer queryAdminCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询创客数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-count-by-phone-number")
    Integer queryMakerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询商户员工数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-enterprise-worker-count-by-phone-number")
    Integer queryEnterpriseWorkerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询服务商员工数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-service-provider-worker-count-by-phone-number")
    Integer queryServiceProviderWorkerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 管理员处理
     *
     * @param phoneNumber
     * @param userName
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/admin-deal")
    R<String> adminDeal(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 创客处理
     *
     * @param openid
     * @param sessionKey
     * @param phoneNumber
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/maker-deal")
    R<String> makerDeal(@RequestParam("openid") String openid, @RequestParam("sessionKey") String sessionKey, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 商户处理
     *
     * @param phoneNumber
     * @param employeeUserName
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/enterprise-worker-deal")
    R<String> enterpriseWorkerDeal(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("employeeUserName") String employeeUserName, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 服务商处理
     *
     * @param phoneNumber
     * @param employeeUserName
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/service-provider-worker-deal")
    R<String> serviceProviderWorkerDeal(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("employeeUserName") String employeeUserName, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 查询个独信息
     *
     * @param individualEnterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-enterprise-by-id")
    IndividualEnterpriseEntity queryIndividualEnterpriseById(@RequestParam("individualEnterpriseId") Long individualEnterpriseId);

    /**
     * 查询个体户信息
     *
     * @param individualBusinessId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-business-by-id")
    IndividualBusinessEntity queryIndividualBusinessById(@RequestParam("individualBusinessId") Long individualBusinessId);

    /**
     * 根据商户查询商户
     *
     * @param enterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-enterprise-by-id")
    EnterpriseEntity queryEnterpriseById(@RequestParam("enterpriseId") Long enterpriseId);

    /**
     * 根据商户, 服务商查询关联
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @return
     */
    @PostMapping(API_PREFIX + "/query-count-by-enterprise-id-and-service-provider-id")
    int queryCountByEnterpriseIdAndServiceProviderId(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("serviceProviderId") Long serviceProviderId, CooperateStatus cooperateStatus);

    /**
     * 查询个独信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-enterprise/find-by-maker-id")
    List<IndividualEnterpriseEntity> queryIndividualEnterpriseFindByMakerId(@RequestParam("makerId") Long makerId);

    /**
     * 查询个体信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-business-by-maker-id")
    List<IndividualBusinessEntity> queryIndividualBusinessByMakerId(@RequestParam("makerId") Long makerId);

    /**
     * 根据创客, 统一社会信用代码查询个独
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-business-by-maker-id-and-ibtax-no")
    IndividualBusinessEntity queryIndividualBusinessByMakerIdAndIbtaxNo(@RequestParam("makerId") Long makerId, @RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 根据创客, 统一社会信用代码查询个体户
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-enterprise-by-maker-id-and-ibtax-no")
    IndividualEnterpriseEntity queryIndividualEnterpriseByMakerIdAndIbtaxNo(@RequestParam("makerId") Long makerId, @RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 统一社会信用代码查询个独
     *
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-business-by-ibtax-no")
    IndividualBusinessEntity queryIndividualBusinessByIbtaxNo(@RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 统一社会信用代码查询个体户
     *
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-enterprise-by-ibtax-no")
    IndividualEnterpriseEntity queryIndividualEnterpriseByIbtaxNo(@RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 新增创客
     *
     * @param name
     * @param idcardNo
     * @param phoneNumber
     * @param enterpriseId
     * @return
     */
    @PostMapping(API_PREFIX + "/create-maker")
    MakerEntity createMaker(@RequestParam("name") String name, @RequestParam("idcardNo") String idcardNo, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("enterpriseId") Long enterpriseId);

    /**
     * 根据商户和创客建立关联关系
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    @PostMapping(API_PREFIX + "/create-maker-to-enterprise-relevance")
    void createMakerToEnterpriseRelevance(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("makerId") Long makerId);


    /**
     * 根据服务商Id查询服务商
     *
     * @param serviceProviderId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-service_provider-by-id")
    ServiceProviderEntity queryServiceProviderById(@RequestParam("serviceProviderId") Long serviceProviderId);


}
