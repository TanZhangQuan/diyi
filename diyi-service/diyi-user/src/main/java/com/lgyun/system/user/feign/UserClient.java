package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessListByMakerDto;
import com.lgyun.system.user.dto.IndividualEnterpriseListByMakerDto;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 用户服务Feign实现类
 *
 * @author liangfeihu
 * @since 2020/6/6 22:11
 */
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

    private IUserService service;
    private IMakerService iMakerService;
    private IIndividualEnterpriseService iIndividualEnterpriseService;
    private IIndividualBusinessService iIndividualBusinessService;
    private IRunCompanyService iRunCompanyService;
    private IEnterpriseService iEnterpriseService;

    @Override
    @GetMapping(API_PREFIX + "/user-info-by-id")
    public UserInfo userInfo(Long userId, UserType userType) {
        return service.userInfo(userId, userType);
    }

    /**
     * 获取用户信息
     *
     * @param phone 用户手机号
     * @return
     */
    @Override
    @GetMapping(API_PREFIX + "/phone")
    public UserInfo userInfoByPhone(String phone, UserType userType) {
        UserInfo userInfo = service.userInfoByPhone(phone, userType);
        return userInfo;
    }

    @Override
    @GetMapping(API_PREFIX + "/user-info")
    public UserInfo userInfo(String account, String password, UserType userType) {
        return service.userInfo(account, password, userType);
    }

    @Override
    public User userFindById(Long id) {
        return service.getById(id);
    }

    @Override
    public MakerEntity makerFindByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd) {
        return iMakerService.findByPhoneNumberAndLoginPwd(phoneNumber, loginPwd);
    }

    @Override
    public MakerEntity makerFindByPhone(String phone) {
        return iMakerService.findByPhoneNumber(phone);
    }

    public void makerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd) {
        //新建管理员
        User user = new User();
        user.setUserType(UserType.MAKER);
        user.setAccount(purePhoneNumber);
        user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
        user.setName("用户");
        user.setRealName("用户");
        user.setEmail("user@bladex.vip");
        user.setPhone(purePhoneNumber);
        user.setBirthday(new Date());
        user.setSex(1);
        service.save(user);

        //新建创客
        MakerEntity makerEntity = new MakerEntity();
        makerEntity.setOpenid(openid);
        makerEntity.setUserId(user.getId());
        makerEntity.setSessionKey(sessionKey);
        makerEntity.setPhoneNumber(purePhoneNumber);
        if (StringUtil.isNotBlank(loginPwd)) {
            makerEntity.setLoginPwd(loginPwd);
        } else {
            makerEntity.setLoginPwd(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
        }
        makerEntity.setRelDate(new Date());
        makerEntity.setMakerState(AccountState.NORMAL);
        makerEntity.setIdcardVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setFaceVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setPhoneNumberVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setBankCardVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setVideoAudit(VideoAudit.TOAUDIT);
        iMakerService.save(makerEntity);
    }

    public void makerUpdate(MakerEntity makerEntity, String openid, String sessionKey) {
        //更新微信信息
        makerEntity.setOpenid(openid);
        makerEntity.setSessionKey(sessionKey);
        iMakerService.updateById(makerEntity);
    }

    @Override
    public R makerSaveOrUpdate(String openid, String sessionKey, String phoneNumber, String loginPwd, GrantType grantType) {

        //根据手机号码查询创客，存在就更新微信信息，不存在就新建创客
        MakerEntity makerEntity;
        switch (grantType) {
            case WECHAT:
                //根据手机号获取创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    makerSave(openid, sessionKey, phoneNumber, "");
                }
                break;

            case PASSWORD:
                //根据手机号密码获取创客
                makerEntity = iMakerService.findByPhoneNumberAndLoginPwd(phoneNumber, loginPwd);
                if (makerEntity != null) {
                    makerUpdate(makerEntity, openid, sessionKey);
                } else {
                    return R.fail("账号或密码错误");
                }
                break;

            case MOBILE:
                //根据手机号获取创客
                makerEntity = iMakerService.findByPhoneNumber(phoneNumber);
                if (makerEntity != null) {
                    makerUpdate(makerEntity, openid, sessionKey);
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
                    makerSave(openid, sessionKey, phoneNumber, loginPwd);
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
    public R findRunCompanyMakerId(Integer current, Integer size, Long makerId) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        return iRunCompanyService.findMakerId(Condition.getPage(query.setDescs("create_time")),makerId);
    }

    @Override
    public R runCompanySave(RunCompanyDto runCompanyDto) {
        return iRunCompanyService.runCompanySave(runCompanyDto);
    }

    @Override
    public R listByMaker(Integer current, Integer size, IndividualEnterpriseListByMakerDto individualEnterpriseListByMakerDto) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        return iIndividualEnterpriseService.listByMaker(Condition.getPage(query.setDescs("create_time")), individualEnterpriseListByMakerDto);
    }

    @Override
    public R listByMaker(Integer current, Integer size, IndividualBusinessListByMakerDto individualBusinessListByMakerDto) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        return iIndividualBusinessService.listByMaker(Condition.getPage(query.setDescs("create_time")), individualBusinessListByMakerDto);
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
    public MakerEntity makerFindByUserId(Long userId) {
        return iMakerService.findByUserId(userId);
    }

}
