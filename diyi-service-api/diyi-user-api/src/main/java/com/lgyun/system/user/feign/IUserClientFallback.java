package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author tzq
 * @since 2020/6/6 00:29
 */
@Component
public class IUserClientFallback implements IUserClient {

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<PartnerEntity> currentPartner(BladeUser bladeUser) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<RelBureauEntity> currentRelBureau(BladeUser bladeUser) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<AgentMainWorkerEntity> currentAgentMainWorker(BladeUser bladeUser) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public AdminEntity queryAdminById(Long adminId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public MakerEntity queryMakerById(Long makerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public PartnerEntity queryPartnerById(Long partnerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public EnterpriseWorkerEntity queryEnterpriseWorkerById(Long enterpriseWorkerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public ServiceProviderWorkerEntity queryServiceProviderWorkerById(Long serviceProviderWorkerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public AgentMainWorkerEntity queryAgentMainWorkerById(Long agentMainWorkerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public RelBureauEntity queryRelBureauById(Long relBureauId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public MakerEntity queryMakerByIdcardNo(String idcardNo) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public MakerEntity queryMakerByPhoneNumber(String phoneNumber) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryMakerEnterpriseRelevanceCount(Long enterpriseId, Long makerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryAdminCountByPhoneNumber(String phoneNumber) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryMakerCountByPhoneNumber(String phoneNumber) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryPartnerCountByPhoneNumber(String phoneNumber) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryEnterpriseWorkerCountByPhoneNumber(String phoneNumber) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryServiceProviderWorkerCountByPhoneNumber(String phoneNumber) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryAgentMainWorkerCountByPhoneNumber(String phoneNumber) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<AdminEntity> adminDeal(String account, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<MakerEntity> makerDeal(String openid, String sessionKey, String account, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<PartnerEntity> partnerDeal(String openid, String sessionKey, String account, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<EnterpriseWorkerEntity> enterpriseWorkerDeal(String account, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<ServiceProviderWorkerEntity> serviceProviderWorkerDeal(String account, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<AgentMainWorkerEntity> agentMainWorkerDeal(String account, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<RelBureauEntity> relBureauDeal(String account, String password, RelBureauType relBureauType, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseById(Long individualEnterpriseId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessById(Long individualBusinessId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryCountByEnterpriseIdAndServiceProviderId(Long enterpriseId, Long serviceProviderId, CooperateStatus cooperateStatus) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryIndividualEnterpriseNumByMakerId(Long makerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryIndividualBusinessNumByMakerId(Long makerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessByIbtaxNo(String ibtaxNo) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByIbtaxNo(String ibtaxNo) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public MakerEntity createMaker(String name, String idcardNo, String phoneNumber, Long enterpriseId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public void createMakerToEnterpriseRelevance(Long enterpriseId, Long makerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryServiceProviderCountById(Long serviceProviderId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryEnterpriseCountById(Long enterpriseId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryValidAgreementNum(ObjectType partyA, Long partyAId, ObjectType partyB, Long partyBId, AgreementType agreementType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> dealMakerRule(Long serviceProviderId, Long makerId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> dealEnterpriseRule(Long serviceProviderId, Long enterpriseId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public AgreementEntity queryEntMakSupplementaryAgreement(Long makerId, Long enterpriseId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public EnterpriseEntity queryEnterpriseById(Long enterpriseId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public EnterpriseEntity queryEnterpriseByName(String enterpriseName) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public void createMakerToEnterpriseSupplement(Long enterpriseId, Long makerId, String businessContract) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public void associatedServiceProviderMaker(Long enterpriseId, Long serviceProviderId, Long makerId, ServiceProviderMakerRelType relType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

}
