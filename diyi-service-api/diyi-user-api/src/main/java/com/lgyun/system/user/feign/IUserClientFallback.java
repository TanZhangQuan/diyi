package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.common.enumeration.UserType;
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
    public UserInfo queryUserInfoByUserId(Long userId, UserType userType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public UserInfo queryUserInfoByPhone(String phone, UserType userType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public UserInfo queryUserInfoByAccount(String account, UserType userType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

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
    public MakerEntity queryMakerById(Long makerId) {
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
    public R<String> adminDeal(String phoneNumber, String userName, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> makerDeal(String openid, String sessionKey, String phoneNumber, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> partnerDeal(String openid, String sessionKey, String phoneNumber, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> enterpriseWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> serviceProviderWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> agentMainWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> relBureauDeal(String phoneNumber, String employeeUserName, String password, RelBureauType relBureauType, GrantType grantType) {
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
    public EnterpriseEntity queryEnterpriseById(Long enterpriseId) {
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
    public ServiceProviderEntity queryServiceProviderById(Long serviceProviderId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public int queryEntMakSupplementaryAgreementNum(Long makerId, Long enterpriseId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

}
