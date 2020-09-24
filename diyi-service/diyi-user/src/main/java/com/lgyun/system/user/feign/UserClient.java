package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.MakerAddDto;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.MakerWorksheetVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
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
    private RedisUtil redisUtil;

    @Override
    public UserInfo userInfoFindByUserIdAndUserType(Long userId, UserType userType) {
        return iUserService.userInfoFindByUserIdAndUserType(userId, userType);
    }

    @Override
    public UserInfo userInfoFindByPhoneAndUserType(String phone, UserType userType) {
        return iUserService.userInfoFindByPhoneAndUserType(phone, userType);
    }

    @Override
    public UserInfo userInfoByAccountAndUserType(String account, UserType userType) {
        return iUserService.userInfoByAccountAndUserType(account, userType);
    }

    @Override
    public MakerEntity makerFindById(Long makerId) {
        return iMakerService.getById(makerId);
    }

    @Override
    public MakerEntity makerFindByIdcardNo(String idcardNo) {
        return iMakerService.findByIdcardNo(idcardNo);
    }

    @Override
    public MakerEntity makerFindByPhoneNumber(String phoneNumber) {
        return iMakerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer adminCountFindByPhoneNumber(String phoneNumber) {
        return iAdminService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer makerCountFindByPhoneNumber(String phoneNumber) {
        return iMakerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer enterpriseWorkerCountFindByPhoneNumber(String phoneNumber) {
        return iEnterpriseWorkerService.findCountByPhoneNumber(phoneNumber);
    }

    @Override
    public Integer serviceProviderWorkerCountFindByPhoneNumber(String phoneNumber) {
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
                iAdminService.save(adminEntity);

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
                iMakerService.save(makerEntity);

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
                iEnterpriseWorkerService.save(enterpriseWorkerEntity);

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
                iServiceProviderWorkerService.save(serviceProviderWorkerEntity);

                break;

            default:
                return R.fail("授权类型有误");
        }

        return R.success("操作成功");
    }

    @Override
    public List<IndividualEnterpriseEntity> individualEnterpriseFindByMakerId(Long makerId) {
        return iIndividualEnterpriseService.findMakerId(makerId);
    }

    @Override
    public List<IndividualBusinessEntity> individualBusinessByMakerId(Long makerId) {
        return iIndividualBusinessService.findMakerId(makerId);
    }

    @Override
    public R<IPage<EnterprisesIdNameListVO>> findEnterpriseByMakerId(Integer current, Integer size, Long makerId) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        return iMakerEnterpriseService.findEnterpriseIdNameByMakerId(Condition.getPage(query.setDescs("create_time")), makerId);
    }

    @Override
    public IndividualEnterpriseEntity individualEnterpriseFindById(Long individualEnterpriseId) {
        return iIndividualEnterpriseService.getById(individualEnterpriseId);
    }

    @Override
    public IndividualBusinessEntity individualBusinessById(Long individualBusinessId) {
        return iIndividualBusinessService.getById(individualBusinessId);
    }

    @Override
    public EnterpriseEntity getEnterpriseById(Long enterpriseId) {
        return iEnterpriseService.getById(enterpriseId);
    }

    @Override
    public R<IPage<MakerWorksheetVO>> getMakerName(Integer current, Integer size, String makerName) {
        return iMakerService.getMakerName(current, size, makerName);
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
    public EnterpriseServiceProviderEntity findByEnterpriseIdServiceProviderId(Long enterpriseId, Long serviceProviderId) {
        return iEnterpriseServiceProviderService.findByEnterpriseIdServiceProviderId(enterpriseId, serviceProviderId);
    }

    @Override
    public IndividualBusinessEntity findByMakerIdAndIbtaxNoBusiness(Long makerId, String ibtaxNo) {
        return iIndividualBusinessService.findByMakerIdAndIbtaxNo(makerId, ibtaxNo);
    }

    @Override
    public IndividualEnterpriseEntity findByMakerIdAndIbtaxNoEnterprise(Long makerId, String ibtaxNo) {
        return iIndividualEnterpriseService.findByMakerIdAndIbtaxNo(makerId, ibtaxNo);
    }


    @Override
    public IndividualBusinessEntity findByIbtaxNoBusiness(String ibtaxNo) {
        return iIndividualBusinessService.findByIbtaxNo(ibtaxNo);
    }

    @Override
    public IndividualEnterpriseEntity findByIbtaxNoEnterprise(String ibtaxNo) {
        return iIndividualEnterpriseService.findByIbtaxNo(ibtaxNo);
    }

    @Override
    public MakerEntity makerAdd(String name, String idcardNo, String phoneNumber, Long enterpriseId) {
        MakerAddDto makerAddDto = new MakerAddDto();
        makerAddDto.setName(name);
        makerAddDto.setIdcardNo(idcardNo);
        makerAddDto.setPhoneNumber(phoneNumber);
        return iMakerService.enterpriseMakerAdd(makerAddDto, enterpriseId);
    }

    @Override
    public void makerEnterpriseAdd(Long enterpriseId, Long makerId) {
        iMakerEnterpriseService.makerEnterpriseEntitySave(enterpriseId, makerId);
    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> getServiceProviderByEnterpriseId(Integer current, Integer size, Long enterpriseId, String serviceProviderName) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        return iEnterpriseServiceProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), enterpriseId, serviceProviderName);
    }

}
