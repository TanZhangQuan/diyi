package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.enumeration.VideoAudit;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
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
    public R<UserInfo> userInfo(Long userId) {
        return R.data(service.userInfo(userId));
    }

    /**
     * 获取用户信息
     *
     * @param phone 用户手机号
     * @return
     */
    @Override
    @GetMapping(API_PREFIX + "/phone")
    public R<UserInfo> userInfoByPhone(String phone) {
        UserInfo userInfo = service.userInfoByPhone(phone);
        return R.data(userInfo);
    }

    @Override
    @GetMapping(API_PREFIX + "/user-info")
    public R<UserInfo> userInfo(String tenantId, String account, String password) {
        return R.data(service.userInfo(tenantId, account, password));
    }

    @Override
    public User userFindById(Long id) {
        return service.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User wechatAuthorization(String openid, String sessionKey, String purePhoneNumber, String tenantId) {

        //根据手机号码查询创客，存在就更新微信信息，不存在就新建创客
        User user;
        MakerEntity makerEntity = iMakerService.findByPhoneNumber(purePhoneNumber);
        if (makerEntity != null) {
            //更新微信信息
            makerEntity.setOpenid(openid);
            makerEntity.setSessionKey(sessionKey);
            makerEntity.setUpdateTime(new Date());
            iMakerService.updateById(makerEntity);
            user = service.getById(makerEntity.getUserId());
        } else {
            //新建管理员
            user = new User();
            user.setTenantId(tenantId);
            user.setAccount(purePhoneNumber);
            user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
            user.setName("用户");
            user.setRealName("用户");
            user.setEmail("user@bladex.vip");
            user.setPhone(purePhoneNumber);
            user.setBirthday(new Date());
            user.setSex(1);
            user.setRoleId("1123598816738675202");
            user.setDeptId("1123598813738675201");
            user.setPostId("1123598817738675208");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setIsDeleted(0);
            user.setStatus(1);
            service.save(user);

            //新建创客
            makerEntity = new MakerEntity();
            makerEntity.setOpenid(openid);
            makerEntity.setUserId(user.getId());
            makerEntity.setSessionKey(sessionKey);
            makerEntity.setPhoneNumber(purePhoneNumber);
            makerEntity.setLoginPwd(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
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

        return user;

    }

    @Override
    public User wechatPassword(String account, String password, String openid, String sessionKey) {

        //根据手机号码查询创客，存在就更新微信信息，不存在就新建创客
        User user = null;
        MakerEntity makerEntity = iMakerService.findByPhoneNumberAndLoginPwd(account, password);
        if (makerEntity != null) {
            //更新微信信息
            makerEntity.setOpenid(openid);
            makerEntity.setSessionKey(sessionKey);
            makerEntity.setUpdateTime(new Date());
            iMakerService.updateById(makerEntity);
            user = service.getById(makerEntity.getUserId());
        }

        return user;
    }

}
