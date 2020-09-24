package com.lgyun.auth.service;

import com.lgyun.auth.dto.*;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.UserType;

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
    R wechatlogin(WechatLoginDto wechatLoginDto) throws Exception;

    /**
     * 发送短信验证码
     *
     * @param sendCodeDto
     * @return
     */
    R sendCode(SendCodeDto sendCodeDto);

    /**
     * 手机验证码登录
     *
     * @param mobileLoginDto
     * @return
     */
    R mobileLogin(MobileLoginDto mobileLoginDto) throws Exception;

    /**
     * 账号密码登录
     *
     * @param passwordLoginDto
     * @return
     */
    R passwordLogin(PasswordLoginDto passwordLoginDto) throws Exception;

    /**
     * 刷新令牌
     *
     * @param refreshToken
     * @param userType
     * @return
     */
    R refreshToken(String refreshToken, UserType userType);

    /**
     * 注册
     *
     * @param registerDto
     * @return
     * @throws Exception
     */
    R register(RegisterDto registerDto);

    /**
     * 生成图形验证码
     *
     * @return
     */
    R captcha();

    /**
     * 修改密码
     *
     * @param updatePasswordDto
     * @return
     */
    R updatePassword(UpdatePasswordDto updatePasswordDto);

}

