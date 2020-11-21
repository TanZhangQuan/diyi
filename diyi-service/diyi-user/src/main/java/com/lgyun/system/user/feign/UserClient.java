package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

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

    private IUserService userService;
    private IAdminService adminService;
    private IMakerService makerService;
    private IPartnerService partnerService;
    private IAgreementService agreementService;
    private IEnterpriseService enterpriseService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IIndividualEnterpriseService individualEnterpriseService;
    private IIndividualBusinessService individualBusinessService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;
    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IAgentMainWorkerService agentMainWorkerService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IServiceProviderService serviceProviderService;
    private IAgentMainService agentMainService;
    private IRelBureauService relBureauService;

    @Override
    public UserInfo queryUserInfoByUserId(Long userId, UserType userType) {
        return userService.queryUserInfoByUserId(userId, userType);
    }

    @Override
    public UserInfo queryUserInfoByPhone(String phone, UserType userType) {
        return userService.queryUserInfoByPhone(phone, userType);
    }

    @Override
    public UserInfo queryUserInfoByAccount(String account, UserType userType) {
        return userService.queryUserInfoByAccount(account, userType);
    }

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {
        return adminService.currentAdmin(bladeUser);
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {
        return makerService.currentMaker(bladeUser);
    }

    @Override
    public R<PartnerEntity> currentPartner(BladeUser bladeUser) {
        return partnerService.currentPartner(bladeUser);
    }

    @Override
    public R<RelBureauEntity> currentRelBureau(BladeUser bladeUser) {
        return relBureauService.currentRelBureau(bladeUser);
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {
        return enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
    }

    @Override
    public R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser) {
        return serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
    }

    @Override
    public R<AgentMainWorkerEntity> currentAgentMainWorker(BladeUser bladeUser) {
        return agentMainWorkerService.currentAgentMainWorker(bladeUser);
    }

    @Override
    public MakerEntity queryMakerById(Long makerId) {
        return makerService.getById(makerId);
    }

    @Override
    public MakerEntity queryMakerByIdcardNo(String idcardNo) {
        return makerService.findByIdcardNo(idcardNo);
    }

    @Override
    public MakerEntity queryMakerByPhoneNumber(String phoneNumber) {
        return makerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public int queryMakerEnterpriseRelevanceCount(Long enterpriseId, Long makerId) {
        return makerEnterpriseService.queryMakerEnterpriseNum(enterpriseId, makerId, RelationshipType.RELEVANCE);
    }

    @Override
    public int queryAdminCountByPhoneNumber(String phoneNumber) {
        return adminService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public int queryMakerCountByPhoneNumber(String phoneNumber) {
        return makerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public int queryEnterpriseWorkerCountByPhoneNumber(String phoneNumber) {
        return enterpriseWorkerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public int queryServiceProviderWorkerCountByPhoneNumber(String phoneNumber) {
        return serviceProviderWorkerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public int queryAgentMainWorkerCountByPhoneNumber(String phoneNumber) {
        return agentMainWorkerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public R<String> adminDeal(String phoneNumber, String userName, String password, GrantType grantType) {

        AdminEntity adminEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询管理员
                adminEntity = adminService.findByUserNameAndLoginPwd(userName, password);
                if (adminEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                break;

            case MOBILE:
                //根据手机号查询管理员
                adminEntity = adminService.findByPhoneNumber(phoneNumber);
                if (adminEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                break;

            case UPDATEPASSWORD:
                //修改管理员密码
                adminEntity = adminService.findByPhoneNumber(phoneNumber);
                if (adminEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                adminEntity.setLoginPwd(password);
                adminService.updateById(adminEntity);

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
                makerEntity = makerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {

                    if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    makerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    makerService.makerSave(openid, sessionKey, phoneNumber, password);
                }

                break;

            case PASSWORD:
                //根据账号密码查询创客
                makerEntity = makerService.findByPhoneNumberAndLoginPwd(phoneNumber, password);
                if (makerEntity != null) {

                    if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    makerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("账号或密码错误");
                }

                break;

            case MOBILE:
                //根据手机号查询创客
                makerEntity = makerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {

                    if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    makerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("手机号未注册");
                }

                break;

            case REGISTER:
                //根据手机号查询创客
                makerEntity = makerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    return R.fail("手机号已注册");
                } else {
                    makerService.makerSave(openid, sessionKey, phoneNumber, password);
                }

                break;

            case UPDATEPASSWORD:
                //根据手机号查询创客
                makerEntity = makerService.findByPhoneNumber(phoneNumber);
                if (makerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(makerEntity.getMakerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                makerEntity.setLoginPwd(password);
                makerService.updateById(makerEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> partnerDeal(String openid, String sessionKey, String phoneNumber, String password, GrantType grantType) {

        PartnerEntity partnerEntity;
        switch (grantType) {

            case WECHAT:
                //根据手机号查询合伙人
                partnerEntity = partnerService.findByPhoneNumber(phoneNumber);
                if (partnerEntity != null) {

                    if (!(AccountState.NORMAL.equals(partnerEntity.getPartnerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    partnerService.partnerUpdate(partnerEntity, openid, sessionKey);
                } else {
                    partnerService.partnerSave(openid, sessionKey, phoneNumber, password);
                }

                break;

            case PASSWORD:
                //根据账号密码查询合伙人
                partnerEntity = partnerService.findByPhoneNumberAndLoginPwd(phoneNumber, password);
                if (partnerEntity != null) {

                    if (!(AccountState.NORMAL.equals(partnerEntity.getPartnerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    partnerService.partnerUpdate(partnerEntity, openid, sessionKey);
                } else {
                    return R.fail("账号或密码错误");
                }

                break;

            case MOBILE:
                //根据手机号查询合伙人
                partnerEntity = partnerService.findByPhoneNumber(phoneNumber);
                if (partnerEntity != null) {

                    if (!(AccountState.NORMAL.equals(partnerEntity.getPartnerState()))) {
                        return R.fail("账号状态非正常，请联系客服");
                    }

                    partnerService.partnerUpdate(partnerEntity, openid, sessionKey);
                } else {
                    return R.fail("手机号未注册");
                }

                break;

            case REGISTER:
                //根据手机号查询合伙人
                partnerEntity = partnerService.findByPhoneNumber(phoneNumber);
                if (partnerEntity != null) {
                    return R.fail("手机号已注册");
                } else {
                    partnerService.partnerSave(openid, sessionKey, phoneNumber, password);
                }

                break;

            case UPDATEPASSWORD:
                //根据手机号查询合伙人
                partnerEntity = partnerService.findByPhoneNumber(phoneNumber);
                if (partnerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(partnerEntity.getPartnerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                partnerEntity.setLoginPwd(password);
                partnerService.updateById(partnerEntity);

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
                enterpriseWorkerEntity = enterpriseWorkerService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, password);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                enterpriseEntity = enterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
                if (enterpriseEntity == null) {
                    return R.fail("商户不存在");
                }

                if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
                    return R.fail("商户状态非正常，请联系客服");
                }

                break;

            case MOBILE:
                //根据手机号查询商户员工
                enterpriseWorkerEntity = enterpriseWorkerService.findByPhoneNumber(phoneNumber);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                enterpriseEntity = enterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
                if (enterpriseEntity == null) {
                    return R.fail("商户不存在");
                }

                if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
                    return R.fail("商户状态非正常，请联系客服");
                }

                break;

            case UPDATEPASSWORD:
                //根据手机号查询商户员工
                enterpriseWorkerEntity = enterpriseWorkerService.findByPhoneNumber(phoneNumber);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                enterpriseEntity = enterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
                if (enterpriseEntity == null) {
                    return R.fail("商户不存在");
                }

                if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
                    return R.fail("商户状态非正常，请联系客服");
                }

                enterpriseWorkerEntity.setEmployeePwd(password);
                enterpriseWorkerService.updateById(enterpriseWorkerEntity);

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
                //根据账号密码查询服务商员工
                serviceProviderWorkerEntity = serviceProviderWorkerService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, password);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                serviceProviderEntity = serviceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
                if (serviceProviderEntity == null) {
                    return R.fail("服务商不存在");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
                    return R.fail("服务商状态非正常，请联系客服");
                }

                break;

            case MOBILE:
                //根据手机号查询服务商员工
                serviceProviderWorkerEntity = serviceProviderWorkerService.findByPhoneNumber(phoneNumber);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                serviceProviderEntity = serviceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
                if (serviceProviderEntity == null) {
                    return R.fail("服务商不存在");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
                    return R.fail("服务商状态非正常，请联系客服");
                }

                break;

            case UPDATEPASSWORD:
                //根据手机号查询服务商员工
                serviceProviderWorkerEntity = serviceProviderWorkerService.findByPhoneNumber(phoneNumber);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                serviceProviderEntity = serviceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
                if (serviceProviderEntity == null) {
                    return R.fail("服务商不存在");
                }

                if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
                    return R.fail("服务商状态非正常，请联系客服");
                }

                serviceProviderWorkerEntity.setEmployeePwd(password);
                serviceProviderWorkerService.updateById(serviceProviderWorkerEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    public R<String> agentMainWorkerDeal(String phoneNumber, String employeeUserName, String password, GrantType grantType) {

        AgentMainWorkerEntity agentMainWorkerEntity;
        AgentMainEntity agentMainEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询渠道商员工
                agentMainWorkerEntity = agentMainWorkerService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, password);
                if (agentMainWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(agentMainWorkerEntity.getAgentMainWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                agentMainEntity = agentMainService.getById(agentMainWorkerEntity.getAgentMainId());
                if (agentMainEntity == null) {
                    return R.fail("渠道商不存在");
                }

                if (!(AccountState.NORMAL.equals(agentMainEntity.getAgentMainState()))) {
                    return R.fail("渠道商状态非正常，请联系客服");
                }

                break;

            case MOBILE:
                //根据账号密码查询渠道商员工
                agentMainWorkerEntity = agentMainWorkerService.findByPhoneNumber(phoneNumber);
                if (agentMainWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(agentMainWorkerEntity.getAgentMainWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                agentMainEntity = agentMainService.getById(agentMainWorkerEntity.getAgentMainId());
                if (agentMainEntity == null) {
                    return R.fail("渠道商不存在");
                }

                if (!(AccountState.NORMAL.equals(agentMainEntity.getAgentMainState()))) {
                    return R.fail("渠道商状态非正常，请联系客服");
                }

                break;

            case UPDATEPASSWORD:
                //根据账号密码查询渠道商员工
                agentMainWorkerEntity = agentMainWorkerService.findByPhoneNumber(phoneNumber);
                if (agentMainWorkerEntity == null) {
                    return R.fail("手机号未注册");
                }

                if (!(AccountState.NORMAL.equals(agentMainWorkerEntity.getAgentMainWorkerState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                agentMainEntity = agentMainService.getById(agentMainWorkerEntity.getAgentMainId());
                if (agentMainEntity == null) {
                    return R.fail("渠道商不存在");
                }

                if (!(AccountState.NORMAL.equals(agentMainEntity.getAgentMainState()))) {
                    return R.fail("渠道商状态非正常，请联系客服");
                }

                agentMainWorkerEntity.setEmployeePwd(password);
                agentMainWorkerService.updateById(agentMainWorkerEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    public R<String> relBureauDeal(String phoneNumber, String employeeUserName, String password, RelBureauType relBureauType, GrantType grantType) {

        RelBureauEntity relBureauEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询相关局
                relBureauEntity = relBureauService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, password, relBureauType);
                if (relBureauEntity == null) {
                    return R.fail("账号或密码错误");
                }

                if (!(AccountState.NORMAL.equals(relBureauEntity.getRelBureauState()))) {
                    return R.fail("账号状态非正常，请联系客服");
                }

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseById(Long individualEnterpriseId) {
        return individualEnterpriseService.getById(individualEnterpriseId);
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessById(Long individualBusinessId) {
        return individualBusinessService.getById(individualBusinessId);
    }

    @Override
    public EnterpriseEntity queryEnterpriseById(Long enterpriseId) {
        return enterpriseService.getById(enterpriseId);
    }

    @Override
    public int queryCountByEnterpriseIdAndServiceProviderId(Long enterpriseId, Long serviceProviderId, CooperateStatus cooperateStatus) {
        return enterpriseServiceProviderService.queryCountByEnterpriseIdAndServiceProviderId(enterpriseId, serviceProviderId, cooperateStatus);
    }

    @Override
    public int queryIndividualEnterpriseNumByMakerId(Long makerId) {
        return individualEnterpriseService.queryIndividualEnterpriseNumByMakerId(makerId);
    }

    @Override
    public int queryIndividualBusinessNumByMakerId(Long makerId) {
        return individualBusinessService.queryIndividualBusinessNumByMakerId(makerId);
    }

    @Override
    public IndividualBusinessEntity queryIndividualBusinessByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        return individualBusinessService.findByMakerIdAndIbtaxNo(makerId, ibtaxNo);
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo) {
        return individualEnterpriseService.findByMakerIdAndIbtaxNo(makerId, ibtaxNo);
    }


    @Override
    public IndividualBusinessEntity queryIndividualBusinessByIbtaxNo(String ibtaxNo) {
        return individualBusinessService.queryIndividualBusinessByIbtaxNo(ibtaxNo);
    }

    @Override
    public IndividualEnterpriseEntity queryIndividualEnterpriseByIbtaxNo(String ibtaxNo) {
        return individualEnterpriseService.queryIndividualEnterpriseByIbtaxNo(ibtaxNo);
    }

    @Override
    public MakerEntity createMaker(String name, String idcardNo, String phoneNumber, Long enterpriseId) {
        return makerService.makerSave(phoneNumber, name, idcardNo, "", "", "", enterpriseId);
    }

    @Override
    public void createMakerToEnterpriseRelevance(Long enterpriseId, Long makerId) {
        makerEnterpriseService.makerEnterpriseEntitySave(enterpriseId, makerId);
    }

    @Override
    public ServiceProviderEntity queryServiceProviderById(Long serviceProviderId) {
        return serviceProviderService.getById(serviceProviderId);
    }

    @Override
    public int queryEntMakSupplementaryAgreementNum(Long makerId, Long enterpriseId) {
        return agreementService.queryEntMakSupplementaryAgreementNum(makerId, enterpriseId);
    }

}
