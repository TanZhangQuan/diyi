package com.lgyun.auth.controller;

import com.lgyun.auth.dto.*;
import com.lgyun.auth.service.IAuthService;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.UserType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 认证模块
 *
 * @author tzq
 * @since 2020/6/6 01:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Api(value = "用户授权认证", tags = "用户授权认证")
public class AuthController {

    private IAuthService authService;

    @PostMapping("/wechat-login")
    @ApiOperation(value = "微信授权登录", notes = "微信授权登录")
    public R wechatlogin(@Valid @RequestBody WechatLoginDTO wechatLoginDto) throws Exception {
        return authService.wechatlogin(wechatLoginDto);
    }

    @PostMapping("/send-code")
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
    public R sendCode(@Valid @RequestBody SendCodeDTO sendCodeDto) {
        return authService.sendCode(sendCodeDto);
    }

    @PostMapping("/mobile-login")
    @ApiOperation(value = "手机验证码登录", notes = "手机验证码登录")
    public R mobileLogin(@Valid @RequestBody MobileLoginDTO mobileLoginDto) throws Exception {
        return authService.mobileLogin(mobileLoginDto);
    }

    @PostMapping("/password-login")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录")
    public R passwordLogin(@Valid @RequestBody PasswordLoginDTO passwordLoginDto) throws Exception {
        return authService.passwordLogin(passwordLoginDto);
    }

    @PostMapping("/refresh-token")
    @ApiOperation(value = "刷新令牌", notes = "刷新令牌")
    public R refreshToken(@ApiParam(value = "令牌", required = true) @NotBlank(message = "请输入令牌") @RequestParam(required = false) String refreshToken,
                                    @ApiParam(value = "用户类型", required = true) @NotBlank(message = "请选择用户类型") @RequestParam(required = false) UserType userType) {

        return authService.refreshToken(refreshToken, userType);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    public R register(@Valid @RequestBody RegisterDTO registerDto) {
        return authService.register(registerDto);
    }

    @GetMapping("/captcha")
    @ApiOperation(value = "生成图形验证码", notes = "生成图形验证码")
    public R captcha() {
        return authService.captcha();
    }

    @PostMapping("/update-password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDto) {
        return authService.updatePassword(updatePasswordDto);
    }

}
