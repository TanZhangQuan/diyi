package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author liangfeihu
 * @since 2020/6/6 00:29
 */
@Component
public class IUserClientFallback implements IUserClient {

    @Override
    public UserInfo queryUserInfoByUserId(Long userId, UserType userType) {
        return null;
    }

    @Override
    public UserInfo queryUserInfoByPhone(String phone, UserType userType) {
        return null;
    }

    @Override
    public UserInfo queryUserInfoByAccount(String account, UserType userType) {
        return null;
    }

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public MakerEntity queryMakerById(Long makerId) {
        return null;
    }

    @Override
    public MakerEntity queryMakerByIdcardNo(String idcardNo) {
        return null;
    }

    @Override
    public MakerEntity queryMakerByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public int queryMakerEnterpriseRelevanceCount(Long enterpriseId, Long makerId) {
        return 0;
    }

    @Override
    public int queryAdminCountByPhoneNumber(String phoneNumber) {
        return 0;
    }

    @Override
    public int queryMakerCountByPhoneNumber(String phoneNumber) {
        return 0;
    }

    @Override
    public int queryEnterpriseWorkerCountByPhoneNumber(String phoneNumber) {
        return 0;
    }

    @Override
    public int queryServiceProviderWorkerCountByPhoneNumber(String phoneNumber) {
        return 0;
    }

    @Override
    public R<String> adminDeal(String phoneNumber, String userName, String password, GrantType grantType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> makerDeal(String openid, String sessionKey, String phoneNumber, String password, GrantType grantType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> enterpriseWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> serviceProviderWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseById(Long individualEnterpriseId) {
        return null;
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessById(Long individualBusinessId) {
        return null;
    }

    @Override
    public EnterpriseEntity queryEnterpriseById(Long enterpriseId) {
        return null;
    }

    @Override
    public int queryCountByEnterpriseIdAndServiceProviderId(Long enterpriseId, Long serviceProviderId, CooperateStatus cooperateStatus) {
        return 0;
    }

    @Override
    public int queryIndividualEnterpriseNumByMakerId(Long makerId) {
        return 0;
    }

    @Override
    public int queryIndividualBusinessNumByMakerId(Long makerId) {
        return 0;
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        return null;
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        return null;
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessByIbtaxNo(String ibtaxNo) {
        return null;
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByIbtaxNo(String ibtaxNo) {
        return null;
    }

    @Override
    public MakerEntity createMaker(String name, String idcardNo, String phoneNumber, Long enterpriseId) {
        return null;
    }

    @Override
    public void createMakerToEnterpriseRelevance(Long enterpriseId, Long makerId) {

    }

    @Override
    public ServiceProviderEntity queryServiceProviderById(Long serviceProviderId) {
        return null;
    }

}
