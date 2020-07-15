package com.lgyun.system.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.dto.IndividualBusinessListByMakerDto;
import com.lgyun.system.user.dto.IndividualEnterpriseListByMakerDto;
import com.lgyun.system.user.dto.RunCompanyDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    public IndividualEnterpriseEntity individualEnterpriseFindByMakerId(Long makerId) {
        return iIndividualEnterpriseService.findMakerId(makerId);
    }

    @Override
    public IndividualBusinessEntity individualBusinessByMakerId(Long makerId) {
        return iIndividualBusinessService.findMakerId(makerId);
    }

    @Override
    public R<IPage<RunCompanyEntity>> findRunCompanyMakerId(IPage<RunCompanyEntity> page, Long makerId) {
        return iRunCompanyService.findMakerId(page,makerId);
    }

    @Override
    public R runCompanySave(RunCompanyDto runCompanyDto, Long makerId) {
        return iRunCompanyService.runCompanySave(runCompanyDto,makerId);
    }

    @Override
    public R<IPage<MakerEntity>> findMakerNamePage(IPage<MakerEntity> page, String name) {
        return iMakerService.findNamePage(page,name);
    }

    @Override
    public R<IPage<IndividualEnterpriseListByMakerVO>> listByMaker(IPage<IndividualEnterpriseListByMakerVO> page, IndividualEnterpriseListByMakerDto individualEnterpriseListByMakerDto) {
        return iIndividualEnterpriseService.listByMaker(page, individualEnterpriseListByMakerDto);
    }

    @Override
    public R<IPage<IndividualBusinessListByMakerVO>> listByMaker(IPage<IndividualBusinessListByMakerVO> page, IndividualBusinessListByMakerDto individualBusinessListByMakerDto) {
        return iIndividualBusinessService.listByMaker(page, individualBusinessListByMakerDto);
    }

    @Override
    public IndividualEnterpriseEntity individualEnterpriseFindById(Long individualEnterpriseId) {
        return iIndividualEnterpriseService.getById(individualEnterpriseId);
    }

    @Override
    public IndividualBusinessEntity individualBusinessById(Long individualBusinessId) {
        return iIndividualBusinessService.getById(individualBusinessId);
    }

}
