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
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.IndividualBusinessEnterpriseListByMakerVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户服务Feign实现类
 *
 * @author liangfeihu
 * @since 2020/6/6 22:11
 */
@Slf4j
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

    private IUserService service;
    private IMakerService iMakerService;
    private IIndividualEnterpriseService iIndividualEnterpriseService;
    private IIndividualBusinessService iIndividualBusinessService;
    private IRunCompanyService iRunCompanyService;
    private IEnterpriseService iEnterpriseService;
    private IEnterpriseWorkerService iEnterpriseWorkerService;
    private IEnterpriseProviderService iEnterpriseProviderService;

    @Override
    @GetMapping(API_PREFIX + "/user-info-by-id")
    public UserInfo userInfo(Long userId, UserType userType) {
        return service.userInfo(userId, userType);
    }

    @Override
    @GetMapping(API_PREFIX + "/phone")
    public UserInfo userInfoByPhone(String phone, UserType userType) {
        return service.userInfoByPhone(phone, userType);
    }

    @Override
    @GetMapping(API_PREFIX + "/user-info")
    public UserInfo userInfo(String account, String password, UserType userType) {
        return service.userInfo(account, password, userType);
    }

    @Override
    public MakerEntity makerFindByPhoneNumber(String phoneNumber) {
        return iMakerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public EnterpriseWorkerEntity enterpriseWorkerFindByPhoneNumber(String phoneNumber) {
        return iEnterpriseWorkerService.findByPhoneNumber(phoneNumber);
    }

    @Override
    public R<String> makerDeal(String openid, String sessionKey, String phoneNumber, String loginPwd, GrantType grantType) {
        log.info("[makerDeal] phone={}", phoneNumber);
        //根据手机号码查询创客，存在就更新微信信息，不存在就新建创客
        MakerEntity makerEntity;
        switch (grantType) {
            case WECHAT:
                //根据手机号获取创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    iMakerService.makerSave(openid, sessionKey, phoneNumber, loginPwd);
                }
                break;

            case PASSWORD:
                //根据账号密码获取创客
                makerEntity = iMakerService.findByPhoneNumberAndLoginPwd(phoneNumber, loginPwd);
                if (makerEntity != null) {
                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("账号或密码错误");
                }
                break;

            case MOBILE:
                //根据手机号获取创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    iMakerService.makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("用户未注册");
                }
                break;

            case REGISTER:
                //根据手机号获取创客
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
    public R<String> enterpriseWorkerDeal(String phoneNumber, String loginPwd, GrantType grantType) {
        log.info("[enterpriseWorkerDeal] phone={}", phoneNumber);
        EnterpriseWorkerEntity enterpriseWorkerEntity;
        switch (grantType) {

            case PASSWORD:
                //根据账号密码获取商户
                enterpriseWorkerEntity = iEnterpriseWorkerService.findByEmployeeUserNameAndEmployeePwd(phoneNumber, loginPwd);
                if (enterpriseWorkerEntity == null) {
                    return R.fail("账号或密码错误");
                }
                break;

            case MOBILE:
                //根据手机号获取商户
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
    public List<IndividualEnterpriseEntity> individualEnterpriseFindByMakerId(Long makerId) {
        return iIndividualEnterpriseService.findMakerId(makerId);
    }

    @Override
    public List<IndividualBusinessEntity> individualBusinessByMakerId(Long makerId) {
        return iIndividualBusinessService.findMakerId(makerId);
    }

    @Override
    public R findRunCompanyMakerId(Integer current, Integer size, Long objectId, ObjectType objectType) {
        return iRunCompanyService.findMakerId(current, size, objectId,objectType);
    }

    @Override
    public R<String> runCompanySave(RunCompanyDto runCompanyDto) {
        return iRunCompanyService.runCompanySave(runCompanyDto);
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListByMakerVO>> individualEnterpriseListByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {
        return iIndividualEnterpriseService.listByMaker(current, size, makerId, ibstate);
    }

    @Override
    public R<IPage<IndividualBusinessEnterpriseListByMakerVO>> individualBusinessListByMaker(Integer current, Integer size, Long makerId, Ibstate ibstate) {
        return iIndividualBusinessService.listByMaker(current, size, makerId, ibstate);
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
    public MakerEntity makerFindById(Long makerId) {
        return iMakerService.getById(makerId);
    }

    @Override
    public R<MakerEntity> currentMaker(BladeUser bladeUser) {
        return iMakerService.currentMaker(bladeUser);
    }

    @Override
    public R getMakerName(Integer current, Integer size, String makerName) {
        return iMakerService.getMakerName(current,size,makerName);
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {
        return iEnterpriseWorkerService.currentEnterpriseWorker(bladeUser);
    }

    @Override
    public EnterpriseProviderEntity findByEnterpriseIdServiceProviderId(Long enterpriseId, Long serviceProviderId) {
        return iEnterpriseProviderService.findByEnterpriseIdServiceProviderId(enterpriseId, serviceProviderId);
    }

}
