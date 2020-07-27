package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;
import com.lgyun.system.user.vo.MakerDetailVO;
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
    public User userFindById(Long id) {
        return null;
    }

    @Override
    public MakerEntity makerFindByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd) {
        return null;
    }

    @Override
    public MakerEntity makerFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public EnterpriseWorkerEntity enterpriseWorkerFindByEmployeeUserNameEmployeePwd(String employeeUserName, String employeePwd) {
        return null;
    }

    @Override
    public EnterpriseWorkerEntity enterpriseWorkerFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public R<String> makerSaveOrUpdate(String openid, String sessionKey, String phoneNumber, String loginPwd, GrantType grantType) {
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
    public R<IPage<RunCompanyEntity>> findRunCompanyMakerId(Integer current, Integer size, Long makerId) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> runCompanySave(RunCompanyDto runCompanyDto) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<IPage<IndividualEnterpriseListByMakerVO>> individualEnterpriseListByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {
        return R.fail("网络繁忙，请稍后尝试");
    }

    @Override
    public R<IPage<IndividualBusinessListByMakerVO>> individualBusinessListByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {
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
    public MakerEntity currentMaker(BladeUser bladeUser) {
        return null;
    }

    @Override
    public R getMakerName(Integer current, Integer size, String makerName) {
        return null;
    }

}
