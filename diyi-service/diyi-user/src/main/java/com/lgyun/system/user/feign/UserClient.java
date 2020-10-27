package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户服务Feign实现类
 *
 * @author tzq
 * @since 2020/6/6 22:11
 */
@Slf4j
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

    private IUserService iUserService;
    private IEnterpriseService iEnterpriseService;
    private IMakerEnterpriseService iMakerEnterpriseService;
    private IIndividualEnterpriseService iIndividualEnterpriseService;
    private IIndividualBusinessService iIndividualBusinessService;
    private IEnterpriseServiceProviderService iEnterpriseServiceProviderService;
    private IServiceProviderWorkerService iServiceProviderWorkerService;
    private IAdminService iAdminService;
    private IMakerService iMakerService;
    private IEnterpriseWorkerService iEnterpriseWorkerService;
    private IServiceProviderService iServiceProviderService;

    @Override
    public UserInfo queryUserInfoByUserId(Long userId, UserType userType) {
        return iUserService.queryUserInfoByUserId(userId, userType);
    }

    @Override
    public UserInfo queryUserInfoByPhone(String phone, UserType userType) {
        return iUserService.queryUserInfoByPhone(phone, userType);
    }

    @Override
    public UserInfo queryUserInfoByAccount(String account, UserType userType) {
        return iUserService.queryUserInfoByAccount(account, userType);
    }

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {
        return iAdminService.currentAdmin(bladeUser);
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {
        return iMakerService.currentMaker(bladeUser);
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {
        return iEnterpriseWorkerService.currentEnterpriseWorker(bladeUser);
    }

    @Override
    public R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser) {
        return iServiceProviderWorkerService.currentServiceProviderWorker(bladeUser);
    }

    @Override
    public MakerEntity queryMakerById(Long makerId) {
        return iMakerService.getById(makerId);
    }

    @Override
    public MakerEntity queryMakerByIdcardNo(String idcardNo) {
        return iMakerService.findByIdcardNo(idcardNo);
    }

    @Override
    public MakerEntity queryMakerByPhoneNumber(String phoneNumber) {
        return iMakerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer queryAdminCountByPhoneNumber(String phoneNumber) {
        return iAdminService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer queryMakerCountByPhoneNumber(String phoneNumber) {
        return iMakerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer queryEnterpriseWorkerCountByPhoneNumber(String phoneNumber) {
        return iEnterpriseWorkerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer queryServiceProviderWorkerCountByPhoneNumber(String phoneNumber) {
        return iServiceProviderWorkerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public R<String> adminDeal(String phoneNumber, String userName, String password, GrantType grantType) {

        AdminEntity adminEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询管理员
                adminEntity = iAdminService.findByUserNameAndLoginPwd(userName, password);
                if (adminEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                break;

            case MOBILE:
                //根据手机号查询管理员
                adminEntity = iAdminService.findByPhoneNumber(phoneNumber);
                if (adminEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                break;

            case UPDATEPASSWORD:
                //修改管理员密码
                adminEntity = iAdminService.findByPhoneNumber(phoneNumber);
                if (adminEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                adminEntity.setLoginPwd(password);
                iAdminService.updateById(adminEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> makerDeal(String openid, String sessionKey, String phoneNumber, String password, GrantType grantType) {

        MakerEntity makerEntity;
        switch (grantType) {

            case WECHAT:
                //根据手机号查询创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {

                    if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    iMakerService.makerSave(openid, sessionKey, phoneNumber, password);
                }

                break;

            case PASSWORD:
                //根据账号密码查询创客
                makerEntity = iMakerService.findByPhoneNumberAndLoginPwd(phoneNumber, password);
                if (makerEntity != null) {

                    if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("账号或密码错误");
                }

                break;

            case MOBILE:
                //根据手机号查询创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {

                    if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("手机号未注册");
                }

                break;

            case REGISTER:
                //根据手机号查询创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    return R.fail("手机号已注册");
                } else {
                    iMakerService.makerSave(openid, sessionKey, phoneNumber, password);
                }

                break;

            case UPDATEPASSWORD:
                //根据手机号查询创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                makerEntity.setLoginPwd(password);
                iMakerService.updateById(makerEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    public R<String> enterpriseWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {

        EnterpriseWorkerEntity enterpriseWorkerEntity;
        EnterpriseEntity enterpriseEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询商户员工
                enterpriseWorkerEntity = iEnterpriseWorkerService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, password);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                enterpriseEntity = iEnterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
                if (enterpriseEntity == null) {
                    return R.fail("商户不存在");
                }

                if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
                    return R.fail("商户状态非正常，请联系客服");
                }

                break;

            case MOBILE:
                //根据手机号查询商户员工
                enterpriseWorkerEntity = iEnterpriseWorkerService.findByPhoneNumber(phoneNumber);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                enterpriseEntity = iEnterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
                if (enterpriseEntity == null) {
                    return R.fail("商户不存在");
                }

                if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
                    return R.fail("商户状态非正常，请联系客服");
                }

                break;

            case UPDATEPASSWORD:
                //根据手机号查询商户员工
                enterpriseWorkerEntity = iEnterpriseWorkerService.findByPhoneNumber(phoneNumber);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                enterpriseEntity = iEnterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
                if (enterpriseEntity == null) {
                    return R.fail("商户不存在");
                }

                if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
                    return R.fail("商户状态非正常，请联系客服");
                }

                enterpriseWorkerEntity.setEmployeePwd(password);
                iEnterpriseWorkerService.updateById(enterpriseWorkerEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    public R<String> serviceProviderWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {

        ServiceProviderWorkerEntity serviceProviderWorkerEntity;
        ServiceProviderEntity serviceProviderEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询服务商
                serviceProviderWorkerEntity = iServiceProviderWorkerService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, password);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                serviceProviderEntity = iServiceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
                if (serviceProviderEntity == null) {
                    return R.fail("服务商不存在");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
                    return R.fail("服务商状态非正常，请联系客服");
                }

                break;

            case MOBILE:
                //根据手机号查询服务商
                serviceProviderWorkerEntity = iServiceProviderWorkerService.findByPhoneNumber(phoneNumber);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                serviceProviderEntity = iServiceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
                if (serviceProviderEntity == null) {
                    return R.fail("服务商不存在");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
                    return R.fail("服务商状态非正常，请联系客服");
                }

                break;

            case UPDATEPASSWORD:
                serviceProviderWorkerEntity = iServiceProviderWorkerService.findByPhoneNumber(phoneNumber);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                serviceProviderEntity = iServiceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
                if (serviceProviderEntity == null) {
                    return R.fail("服务商不存在");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
                    return R.fail("服务商状态非正常，请联系客服");
                }

                serviceProviderWorkerEntity.setEmployeePwd(password);
                iServiceProviderWorkerService.updateById(serviceProviderWorkerEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseById(Long individualEnterpriseId) {
        return iIndividualEnterpriseService.getById(individualEnterpriseId);
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessById(Long individualBusinessId) {
        return iIndividualBusinessService.getById(individualBusinessId);
    }

    @Override
    public EnterpriseEntity queryEnterpriseById(Long enterpriseId) {
        return iEnterpriseService.getById(enterpriseId);
    }

    @Override
    public EnterpriseServiceProviderEntity queryEnterpriseToServiceProvider(Long enterpriseId, Long serviceProviderId) {
        return iEnterpriseServiceProviderService.findByEnterpriseIdServiceProviderId(enterpriseId, serviceProviderId);
    }

    @Override
    public List<IndividualEnterpriseEntity> queryIndividualEnterpriseFindByMakerId(Long makerId) {
        return iIndividualEnterpriseService.findMakerId(makerId);
    }

    @Override
    public List<IndividualBusinessEntity> queryIndividualBusinessByMakerId(Long makerId) {
        return iIndividualBusinessService.queryIndividualBusinessByMakerId(makerId);
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        return iIndividualBusinessService.findByMakerIdAndIbtaxNo(makerId, ibtaxNo);
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        return iIndividualEnterpriseService.findByMakerIdAndIbtaxNo(makerId, ibtaxNo);
    }


    @Override
    public IndividualBusinessEntity queryIndividualBusinessByIbtaxNo(String ibtaxNo) {
        return iIndividualBusinessService.queryIndividualBusinessByIbtaxNo(ibtaxNo);
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByIbtaxNo(String ibtaxNo) {
        return iIndividualEnterpriseService.queryIndividualEnterpriseByIbtaxNo(ibtaxNo);
    }

    @Override
    public MakerEntity createMaker(String name, String idcardNo, String phoneNumber, Long enterpriseId) {
        return iMakerService.makerSave(phoneNumber, name, idcardNo, "", "", "", enterpriseId);
    }

    @Override
    public void createMakerToEnterpriseRelevance(Long enterpriseId, Long makerId) {
        iMakerEnterpriseService.makerEnterpriseEntitySave(enterpriseId, makerId);
    }

    @Override
    public ServiceProviderEntity queryServiceProviderById(Long serviceProviderId) {
        return iServiceProviderService.getById(serviceProviderId);
    }

}
