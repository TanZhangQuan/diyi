package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.MakerWorksheetVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

;

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
    @GetMapping(API_PREFIX + "/user-info-find-by-user-id-and-user-type")
    UserInfo userInfoFindByUserIdAndUserType(@RequestParam("userId") Long userId, @RequestParam("userType") UserType userType);

    /**
     * 查询用户信息
     *
     * @param phone
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/user-info-find-by-phone-and-user-type")
    UserInfo userInfoFindByPhoneAndUserType(@RequestParam("phone") String phone, @RequestParam("userType") UserType userType);

    /**
     * 查询用户信息
     *
     * @param account
     * @param userType
     * @return
     */
    @GetMapping(API_PREFIX + "/user-info-find-by-account-and-password-and-user-type")
    UserInfo userInfoByAccountAndUserType(@RequestParam("account") String account, @RequestParam("userType") UserType userType);

    /**
     * 根据创客id查询创客信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-find-by-id")
    MakerEntity makerFindById(@RequestParam("makerId") Long makerId);

    /**
     * 根据身份证号码查询创客信息
     *
     * @param idcardNo
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-find-by-idcard-no")
    MakerEntity makerFindByIdcardNo(@RequestParam("idcardNo") String idcardNo);

    /**
     * 查询创客信息
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-find-by-phone-number")
    MakerEntity makerFindByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询管理员是否存在
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/admin-count-find-by-phone-umber")
    Integer adminCountFindByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询创客是否存在
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/maker-count-find-by-phone-number")
    Integer makerCountFindByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询商户员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/enterprise-worker-count-find-by-phone-number")
    Integer enterpriseWorkerCountFindByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询服务商员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/service-provider-worker-count-find-by-phone-number")
    Integer serviceProviderWorkerCountFindByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

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
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualEnterprise/find-by-maker-id")
    List<IndividualEnterpriseEntity> individualEnterpriseFindByMakerId(@RequestParam("makerId") Long makerId);

    /**
     * 查询个体信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualBusiness/find-by-maker-id")
    List<IndividualBusinessEntity> individualBusinessByMakerId(@RequestParam("makerId") Long makerId);

    /**
     * 根据创客ID查询商户
     *
     * @param current
     * @param size
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/find-enterprise-by-maker-id")
    R<IPage<EnterprisesIdNameListVO>> findEnterpriseByMakerId(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam("makerId") Long makerId);

    /**
     * 根据Id查询个独或个体户信息
     *
     * @param individualEnterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualEnterprise/find-by-id")
    IndividualEnterpriseEntity individualEnterpriseFindById(@RequestParam("individualEnterpriseId") Long individualEnterpriseId);

    /**
     * 根据Id查询个体信息
     *
     * @param individualBusinessId
     * @return
     */
    @GetMapping(API_PREFIX + "/individualBusiness/find-by-id")
    IndividualBusinessEntity individualBusinessById(@RequestParam("individualBusinessId") Long individualBusinessId);

    /**
     * 根据商户id查询商户
     *
     * @param enterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/enterprise/get-by-id")
    EnterpriseEntity getEnterpriseById(@RequestParam("enterpriseId") Long enterpriseId);

    /**
     * 根据user-id查询管理员
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-admin")
    R<AdminEntity> currentAdmin(@RequestBody BladeUser bladeUser);

    /**
     * 根据user-id查询创客
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-maker")
    R<MakerEntity> currentMaker(@RequestBody BladeUser bladeUser);

    /**
     * 根据user-id查询商户
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-enterprise-worker")
    R<EnterpriseWorkerEntity> currentEnterpriseWorker(@RequestBody BladeUser bladeUser);

    /**
     * 根据user-id查询服务商
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-service-provider-worker")
    R<ServiceProviderWorkerEntity> currentServiceProviderWorker(@RequestBody BladeUser bladeUser);

    /**
     * 根据创客姓名分页查询
     *
     * @param current
     * @param size
     * @param makerName
     * @return
     */
    @PostMapping(API_PREFIX + "/maker/getMakerName")
    R<IPage<MakerWorksheetVO>> getMakerName(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam(name = "makerName", required = false) String makerName);

    /**
     * 根据商户ID, 服务商ID查询关联
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @return
     */
    @PostMapping(API_PREFIX + "/find-by-enterprise-id-service-provider-id")
    EnterpriseServiceProviderEntity findByEnterpriseIdServiceProviderId(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("serviceProviderId") Long serviceProviderId);

    /**
     * 根据创客ID, 统一社会信用代码查询个独
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/find-by-maker-id-and-ibtax-no-business")
    IndividualBusinessEntity findByMakerIdAndIbtaxNoBusiness(@RequestParam("makerId") Long makerId, @RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 根据创客ID, 统一社会信用代码查询个体户
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/find-by-maker-id-and-ibtax-no-enterprise")
    IndividualEnterpriseEntity findByMakerIdAndIbtaxNoEnterprise(@RequestParam("makerId") Long makerId, @RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 统一社会信用代码查询个独
     *
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/find-by-ibtax-no-business")
    IndividualBusinessEntity findByIbtaxNoBusiness(@RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 统一社会信用代码查询个体户
     *
     * @param ibtaxNo
     * @return
     */
    @GetMapping(API_PREFIX + "/find-by-ibtax-no-enterprise")
    IndividualEnterpriseEntity findByIbtaxNoEnterprise(@RequestParam("ibtaxNo") String ibtaxNo);

    /**
     * 新增创客
     *
     * @param name
     * @param idcardNo
     * @param phoneNumber
     * @param enterpriseId
     * @return
     */
    @PostMapping(API_PREFIX + "/maker-add")
    MakerEntity makerAdd(@RequestParam("name") String name, @RequestParam("idcardNo") String idcardNo, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("enterpriseId") Long enterpriseId);

    /**
     * 根据商户id和创客id建立关联关系
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    @PostMapping(API_PREFIX + "/maker-enterprise-add")
    void makerEnterpriseAdd(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("makerId") Long makerId);

    /**
     * 查询当前商户关联服务商
     *
     * @param current
     * @param size
     * @param enterpriseId
     * @param serviceProviderName
     * @return
     */
    @GetMapping(API_PREFIX + "/get-service-provider-by-enterprise-id")
    R<IPage<ServiceProviderIdNameListVO>> getServiceProviderByEnterpriseId(@RequestParam("current") Integer current, @RequestParam("size") Integer size, @RequestParam(name = "enterpriseId") Long enterpriseId, @RequestParam(name = "serviceProviderName", required = false) String serviceProviderName);

}
