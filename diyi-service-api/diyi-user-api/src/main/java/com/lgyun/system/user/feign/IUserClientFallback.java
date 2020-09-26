package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author liangfeihu
 * @since 2020/6/6 00:29
 */
@Component
public class IUserClientFallback implements IUserClient {

    @Override
    public UserInfo userInfoFindByUserIdAndUserType(Long userId, UserType userType) {
        return null;
    }

    @Override
    public UserInfo userInfoFindByPhoneAndUserType(String phone, UserType userType) {
        return null;
    }

    @Override
    public UserInfo userInfoByAccountAndUserType(String account, UserType userType) {
        return null;
    }

    @Override
    public MakerEntity makerFindById(Long makerId) {
        return null;
    }

    @Override
    public MakerEntity makerFindByIdcardNo(String idcardNo) {
        return null;
    }

    @Override
    public MakerEntity makerFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Integer adminCountFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Integer makerCountFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Integer enterpriseWorkerCountFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Integer serviceProviderWorkerCountFindByPhoneNumber(String phoneNumber) {
        return null;
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
    public List<IndividualEnterpriseEntity> individualEnterpriseFindByMakerId(Long makerId) {
        return null;
    }

    @Override
    public List<IndividualBusinessEntity> individualBusinessByMakerId(Long makerId) {
        return null;
    }

    @Override
    public R<IPage<EnterprisesIdNameListVO>> findEnterpriseByMakerId(Integer current, Integer size, Long makerId) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public IndividualEnterpriseEntity individualEnterpriseFindById(Long individualEnterpriseId) {
        return null;
    }

    @Override
    public IndividualBusinessEntity individualBusinessById(Long individualBusinessId) {
        return null;
    }

    @Override
    public EnterpriseEntity getEnterpriseById(Long enterpriseId) {
        return null;
    }

    @Override
    public R getMakerName(Integer current, Integer size, String makerName) {
        return R.fail("网络繁忙，请稍后尝试");
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
    public EnterpriseServiceProviderEntity findByEnterpriseIdServiceProviderId(Long enterpriseId, Long serviceProviderId) {
        return null;
    }

    @Override
    public IndividualBusinessEntity findByMakerIdAndIbtaxNoBusiness(Long makerId, String ibtaxNo) {
        return null;
    }

    @Override
    public IndividualEnterpriseEntity findByMakerIdAndIbtaxNoEnterprise(Long makerId, String ibtaxNo) {
        return null;
    }

    @Override
    public IndividualBusinessEntity findByIbtaxNoBusiness(String ibtaxNo) {
        return null;
    }

    @Override
    public IndividualEnterpriseEntity findByIbtaxNoEnterprise(String ibtaxNo) {
        return null;
    }

    @Override
    public MakerEntity makerAdd(String name, String idcardNo, String phoneNumber,Long enterpriseId) {
        return null;
    }

    @Override
    public void makerEnterpriseAdd(Long enterpriseId, Long makerId) {

    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> getServiceProviderByEnterpriseId(Integer current, Integer size, Long enterpriseId, String serviceProviderName) {
        return R.fail("网络繁忙，请稍后尝试");
    }

}
