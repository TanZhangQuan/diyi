package com.lgyun.auth.service;

import com.lgyun.auth.dto.*;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-17 14:38:25
 */
public interface IAuthService {

    /**
     * 微信授权登录
     *
     * @param wechatLoginDto
     * @return
     * @throws Exception
     */
    R wechatlogin(WechatLoginDTO wechatLoginDto) throws Exception;

    /**
     * 发送短信验证码
     *
     * @param sendCodeDto
     * @return
     */
    R<String> sendCode(SendCodeDTO sendCodeDto);

    /**
     * 手机验证码登录
     *
     * @param mobileLoginDto
     * @return
     */
    R mobileLogin(MobileLoginDTO mobileLoginDto) throws Exception;

    /**
     * 账号密码登录
     *
     * @param passwordLoginDto
     * @return
     */
    R passwordLogin(PasswordLoginDTO passwordLoginDto) throws Exception;

    /**
     * 刷新令牌
     *
     * @param refreshToken
     * @param userType
     * @return
     */
    R<AuthInfo> refreshToken(String refreshToken, UserType userType);

    /**
     * 注册
     *
     * @param registerDto
     * @return
     * @throws Exception
     */
    R<String> register(RegisterDTO registerDto);

    /**
     * 修改密码
     *
     * @param updatePasswordDto
     * @return
     */
    R<String> updatePassword(UpdatePasswordDTO updatePasswordDto);

}

