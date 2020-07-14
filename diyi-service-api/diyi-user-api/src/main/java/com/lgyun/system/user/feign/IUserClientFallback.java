package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.system.user.dto.RunCompanyDto;
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
    public MakerEntity makerFindByPhone(String phone) {
        return null;
    }

    @Override
    public R makerSaveOrUpdate(String openid, String sessionKey, String phoneNumber, String loginPwd, GrantType grantType) {
        return R.fail("未获取到账号信息");
    }

    @Override
    public IndividualEnterpriseEntity individualEnterpriseFindByMakerId(Long makerId) {
        return null;
    }

    @Override
    public IndividualBusinessEntity individualBusinessByMakerId(Long makerId) {
        return null;
    }

    @Override
    public R<IPage<RunCompanyEntity>> findRunCompanyMakerId(IPage<RunCompanyEntity> page, Long makerId) {
        return null;
    }

    @Override
    public R runCompanySave(RunCompanyDto runCompanyDto, Long makerId) {
        return null;
    }

    @Override
    public R<IPage<MakerEntity>> findMakerNamePage(IPage<MakerEntity> page, String name) {
        return null;
    }

}
