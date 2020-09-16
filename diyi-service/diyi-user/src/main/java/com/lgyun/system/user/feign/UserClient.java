package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
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
    private IMakerService iMakerService;
    private IMakerEnterpriseService iMakerEnterpriseService;
    private IIndividualEnterpriseService iIndividualEnterpriseService;
    private IIndividualBusinessService iIndividualBusinessService;
    private IEnterpriseService iEnterpriseService;
    private IEnterpriseWorkerService iEnterpriseWorkerService;
    private IEnterpriseServiceProviderService iEnterpriseServiceProviderService;
    private IServiceProviderWorkerService iServiceProviderWorkerService;

    @Override
    public UserInfo userInfo(Long userId, UserType userType) {
        return iUserService.userInfo(userId, userType);
    }

    @Override
    public UserInfo userInfoByPhone(String phone, UserType userType) {
        return iUserService.userInfoByPhone(phone, userType);
    }

    @Override
    public User userByPhone(String phone, UserType userType) {
        return iUserService.findByPhone(phone, userType);
    }

    @Override
    public UserInfo userInfo(String account, String password, UserType userType) {
        return iUserService.userInfo(account, password, userType);
    }

    @Override
    public MakerEntity makerFindById(Long makerId) {
        return iMakerService.getById(makerId);
    }

    @Override
    public MakerEntity makerFindByPhoneNumber(String phoneNumber) {
        return iMakerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public MakerEntity makerFindByIdcardNo(String idcardNo) {
        return iMakerService.findByIdcardNo(idcardNo);
    }

    @Override
    public EnterpriseWorkerEntity enterpriseWorkerFindByPhoneNumber(String phoneNumber) {
        return iEnterpriseWorkerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public ServiceProviderWorkerEntity serviceProviderWorkerFindByPhoneNumber(String phoneNumber) {
        return iServiceProviderWorkerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public R<String> makerDeal(String openid, String sessionKey, String phoneNumber, String loginPwd, GrantType grantType) {
        log.info("[makerDeal] phone={}", phoneNumber);
        //根据手机号码查询创客，存在就更新微信信息，不存在就新建创客
        MakerEntity makerEntity;
        switch (grantType) {
            case WECHAT:
                //根据手机号查询创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    iMakerService.makerSave(openid, sessionKey, phoneNumber, loginPwd);
                }
                break;

            case PASSWORD:
                //根据账号密码查询创客
                makerEntity = iMakerService.findByPhoneNumberAndLoginPwd(phoneNumber, loginPwd);
                if (makerEntity != null) {
                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("账号或密码错误");
                }
                break;

            case MOBILE:
                //根据手机号查询创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("用户未注册");
                }
                break;

            case REGISTER:
                //根据手机号查询创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    return R.fail("手机号已注册");
                } else {
                    iMakerService.makerSave(openid, sessionKey, phoneNumber, loginPwd);
                }
                break;

            default:
                return R.fail("登陆方式有误");
        }

        return R.success("操作成功");
    }

    @Override
    public R<String> enterpriseWorkerDeal(String phoneNumber, String employeeUserName, String loginPwd, GrantType grantType) {

        EnterpriseWorkerEntity enterpriseWorkerEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询商户
                enterpriseWorkerEntity = iEnterpriseWorkerService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, loginPwd);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }
                break;

            case MOBILE:
                //根据手机号查询商户
                enterpriseWorkerEntity = iEnterpriseWorkerService.findByPhoneNumber(phoneNumber);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("用户未注册");
                }
                break;

            default:
                return R.fail("登陆方式有误");
        }

        return R.success("操作成功");
    }

    @Override
    public R<String> serviceProviderWorkerDeal(String phoneNumber, String employeeUserName, String loginPwd, GrantType grantType) {

        ServiceProviderWorkerEntity serviceProviderWorkerEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码查询服务商
                serviceProviderWorkerEntity = iServiceProviderWorkerService.findByEmployeeUserNameAndEmployeePwd(employeeUserName, loginPwd);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }
                break;

            case MOBILE:
                //根据手机号查询服务商
                serviceProviderWorkerEntity = iServiceProviderWorkerService.findByPhoneNumber(phoneNumber);
                if (serviceProviderWorkerEntity == null) {
                    return R.fail("用户未注册");
                }
                break;

            default:
                return R.fail("登陆方式有误");
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
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {
        return iMakerService.currentMaker(bladeUser);
    }

    @Override
    public R<IPage<MakerWorksheetVO>> getMakerName(Integer current, Integer size, String makerName) {
        return iMakerService.getMakerName(current, size, makerName);
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
    public R<User> currentUser(BladeUser bladeUser) {
        return iUserService.currentUser(bladeUser);
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
