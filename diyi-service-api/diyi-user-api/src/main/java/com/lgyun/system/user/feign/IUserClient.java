package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * 查询当前合伙人
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-partner")
    R<PartnerEntity> currentPartner(@RequestBody BladeUser bladeUser);

    /**
     * 查询当前相关局
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-rel-bureau")
    R<RelBureauEntity> currentRelBureau(@RequestBody BladeUser bladeUser);

    /**
     * 查询当前商户员工
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-enterprise-worker")
    R<EnterpriseWorkerEntity> currentEnterpriseWorker(@RequestBody BladeUser bladeUser);

    /**
     * 查询当前服务商员工
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-service-provider-worker")
    R<ServiceProviderWorkerEntity> currentServiceProviderWorker(@RequestBody BladeUser bladeUser);

    /**
     * 查询当前渠道商员工
     *
     * @param bladeUser
     * @return
     */
    @PostMapping(API_PREFIX + "/current-agent-main-worker")
    R<AgentMainWorkerEntity> currentAgentMainWorker(@RequestBody BladeUser bladeUser);

    /**
     * 根据管理员ID查询管理员
     *
     * @param adminId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-admin-by-id")
    AdminEntity queryAdminById(@RequestParam("adminId") Long adminId);

    /**
     * 根据创客ID查询创客
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-by-id")
    MakerEntity queryMakerById(@RequestParam("makerId") Long makerId);

    /**
     * 根据合伙人ID查询合伙人
     *
     * @param partnerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-partner-by-id")
    PartnerEntity queryPartnerById(@RequestParam("partnerId") Long partnerId);

    /**
     * 根据商户员工ID查询商户员工
     *
     * @param enterpriseWorkerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-enterprise-worker-by-id")
    EnterpriseWorkerEntity queryEnterpriseWorkerById(@RequestParam("enterpriseWorkerId") Long enterpriseWorkerId);

    /**
     * 根据服务商员工ID查询服务商员工
     *
     * @param serviceProviderWorkerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-service-provider-worker-by-id")
    ServiceProviderWorkerEntity queryServiceProviderWorkerById(@RequestParam("serviceProviderWorkerId") Long serviceProviderWorkerId);

    /**
     * 根据渠道商员工ID查询渠道商员工
     *
     * @param agentMainWorkerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-agent_main-worker-by-id")
    AgentMainWorkerEntity queryAgentMainWorkerById(@RequestParam("agentMainWorkerId") Long agentMainWorkerId);

    /**
     * 根据相关局ID查询相关局
     *
     * @param relBureauId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-rel-bureau-by-id")
    RelBureauEntity queryRelBureauById(@RequestParam("relBureauId") Long relBureauId);

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
     * 查询创客商户是否关联
     *
     * @param makerId
     * @param enterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-enterprise-relevance-count")
    int queryMakerEnterpriseRelevanceCount(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("makerId") Long makerId);

    /**
     * 根据手机号查询管理员数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-admin-count-by-phone-number")
    int queryAdminCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询创客数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-maker-count-by-phone-number")
    int queryMakerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询合伙人数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-partner-count-by-phone-number")
    int queryPartnerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询商户员工数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-enterprise-worker-count-by-phone-number")
    int queryEnterpriseWorkerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询服务商员工数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-service-provider-worker-count-by-phone-number")
    int queryServiceProviderWorkerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询服务商员工数量
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(API_PREFIX + "/query-agent-main-worker-count-by-phone-number")
    int queryAgentMainWorkerCountByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 管理员处理
     *
     * @param account
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/admin-deal")
    R<AdminEntity> adminDeal(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 创客处理
     *
     * @param openid
     * @param sessionKey
     * @param account
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/maker-deal")
    R<MakerEntity> makerDeal(@RequestParam("openid") String openid, @RequestParam("sessionKey") String sessionKey, @RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 合伙人处理
     *
     * @param openid
     * @param sessionKey
     * @param account
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/partner-deal")
    R<PartnerEntity> partnerDeal(@RequestParam("openid") String openid, @RequestParam("sessionKey") String sessionKey, @RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 商户处理
     *
     * @param account
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/enterprise-worker-deal")
    R<EnterpriseWorkerEntity> enterpriseWorkerDeal(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 服务商处理
     *
     * @param account
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/service-provider-worker-deal")
    R<ServiceProviderWorkerEntity> serviceProviderWorkerDeal(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 服务商处理
     *
     * @param account
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/agent-main-worker-deal")
    R<AgentMainWorkerEntity> agentMainWorkerDeal(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("grantType") GrantType grantType);

    /**
     * 相关局处理
     *
     * @param account
     * @param password
     * @param grantType
     * @return
     */
    @PostMapping(API_PREFIX + "/rel-bureau-deal")
    R<RelBureauEntity> relBureauDeal(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("relBureauType") RelBureauType relBureauType, @RequestParam("grantType") GrantType grantType);

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
    @GetMapping(API_PREFIX + "/query-individual-enterprise-num-by-maker-id")
    int queryIndividualEnterpriseNumByMakerId(@RequestParam("makerId") Long makerId);

    /**
     * 查询个体信息
     *
     * @param makerId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-individual-business-num-by-maker-id")
    int queryIndividualBusinessNumByMakerId(@RequestParam("makerId") Long makerId);

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

    /**
     * 查询已签署已审核通过的商户-创客补充协议
     *
     * @param makerId
     * @param enterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-agreement-num")
    int queryEntMakSupplementaryAgreementNum(@RequestParam("makerId") Long makerId, @RequestParam("enterpriseId") Long enterpriseId);


    /**
     * 查询已签署已审核通过的商户-创客补充协议
     *
     * @param makerId
     * @param enterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-agreement")
    AgreementEntity queryEntMakSupplementaryAgreement(@RequestParam("makerId") Long makerId, @RequestParam("enterpriseId") Long enterpriseId);

    /**
     * 根据商户id查询商户
     * @param enterpriseId
     * @return
     */
    @GetMapping(API_PREFIX + "/query-enterprise-by-id")
    EnterpriseEntity queryEnterpriseById(@RequestParam("enterpriseId") Long enterpriseId);

    /**
     * 根据商户名字查询商户
     * @param enterpriseName
     * @return
     */
    @GetMapping(API_PREFIX + "/query-enterprise-by-Name")
    EnterpriseEntity queryEnterpriseByName(@RequestParam("enterpriseName") String enterpriseName);

    /**
     * 创建商户-创客补充协议
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    @PostMapping(API_PREFIX + "/create-maker-to-enterprise-supplement")
    void createMakerToEnterpriseSupplement(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("makerId") Long makerId,@RequestParam("businessContract") String businessContract);

}
