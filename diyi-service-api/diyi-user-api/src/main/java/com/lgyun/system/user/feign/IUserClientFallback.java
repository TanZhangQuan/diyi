package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseListByMakerVO;
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
    public UserInfo userInfo(Long userId, UserType userType) {
        return null;
    }

    @Override
    public UserInfo userInfo(String account, String password, UserType userType) {
        return null;
    }

    @Override
    public UserInfo userInfoByPhone(String phone, UserType userType) {
        return null;
    }

    @Override
    public MakerEntity makerFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public EnterpriseWorkerEntity enterpriseWorkerFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public R<String> makerDeal(String openid, String sessionKey, String phoneNumber, String loginPwd, GrantType grantType) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> enterpriseWorkerDeal(String phoneNumber, String loginPwd, GrantType grantType) {
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
    public R findRunCompanyMakerId(Integer current, Integer size, Long objectId, ObjectType objectType) {
        return R.fail("网络繁忙，请稍后尝试");
    }


    @Override
    public R<String> runCompanySave(RunCompanyDto runCompanyDto) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListByMakerVO>> individualEnterpriseListByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListByMakerVO>> individualBusinessListByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {
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
    public MakerEntity makerFindById(Long makerId) {
        return null;
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R getMakerName(Integer current, Integer size, String makerName) {
        return null;
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public EnterpriseProviderEntity findByEnterpriseIdServiceProviderId(Long enterpriseId, Long serviceProviderId) {
        return null;
    }

}
