package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.enumeration.VideoAudit;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IUserService;
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
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);
        user.setStatus(1);
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
        makerEntity.setIdcardVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setFaceVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setPhoneNumberVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setBankCardVerifyStatus(VerifyStatus.TOVERIFY);
        makerEntity.setVideoAudit(VideoAudit.TOAUDIT);
        makerEntity.setCreateTime(new Date());
        makerEntity.setUpdateTime(new Date());
        makerEntity.setIsDeleted(0);
        makerEntity.setStatus(1);
        iMakerService.save(makerEntity);
    }

    public void makerUpdate(MakerEntity makerEntity, String openid, String sessionKey) {
        //更新微信信息
        makerEntity.setOpenid(openid);
        makerEntity.setSessionKey(sessionKey);
        makerEntity.setUpdateTime(new Date());
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

}
