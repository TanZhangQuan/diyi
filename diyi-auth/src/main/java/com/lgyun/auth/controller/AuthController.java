package com.lgyun.auth.controller;

import com.lgyun.auth.dto.*;
import com.lgyun.auth.granter.ITokenGranter;
import com.lgyun.auth.granter.MobileTokenGranter;
import com.lgyun.auth.granter.TokenGranterBuilder;
import com.lgyun.auth.granter.TokenParameter;
import com.lgyun.common.api.R;
import com.lgyun.common.cache.CacheNames;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.support.Kv;
import com.lgyun.common.tool.RedisUtil;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证模块
 *
 * @author liangfeihu
 * @since 2020/6/6 01:04
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Api(value = "用户授权认证", tags = "用户授权认证")
public class AuthController {

    private RedisUtil redisUtil;
    private MobileTokenGranter mobileTokenGranter;

    @PostMapping("/wechat-login")
    @ApiOperation(value = "微信授权登陆", notes = "微信授权登陆")
    public R wechatlogin(@Valid @RequestBody WechatLoginDto wechatLoginDto) {

        log.info("微信授权登陆");
        try {
            TokenParameter tokenParameter = new TokenParameter();
            tokenParameter.getArgs()
                    .set("userType", wechatLoginDto.getUserType())
                    .set("wechatCode", wechatLoginDto.getWechatCode())
                    .set("iv", wechatLoginDto.getIv())
                    .set("encryptedData", wechatLoginDto.getEncryptedData());

            ITokenGranter granter = TokenGranterBuilder.getGranter(GrantType.WECHAT);
            return granter.grant(tokenParameter);

        } catch (Exception e) {
            log.error("微信授权登陆异常", e);
        }

        return R.fail("登陆失败");
    }

    @PostMapping("/mobile-login")
    @ApiOperation(value = "手机验证码登陆", notes = "手机验证码登陆")
    public R mobileLogin(@Valid @RequestBody MobileLoginDto mobileLoginDto) {

        log.info("手机验证码登陆");
        try {
            TokenParameter tokenParameter = new TokenParameter();
            tokenParameter.getArgs()
                    .set("userType", mobileLoginDto.getUserType())
                    .set("mobile", mobileLoginDto.getMobile())
                    .set("smsCode", mobileLoginDto.getSmsCode())
                    .set("wechatCode", mobileLoginDto.getWechatCode());

            ITokenGranter granter = TokenGranterBuilder.getGranter(GrantType.MOBILE);
            return granter.grant(tokenParameter);

        } catch (Exception e) {
            log.error("手机验证码登陆异常", e);
        }

        return R.fail("登陆失败");
    }

    @PostMapping("/password-login")
    @ApiOperation(value = "账号密码登陆", notes = "账号密码登陆")
    public R passwordLogin(@Valid @RequestBody PasswordLoginDto passwordLoginDto) {

        log.info("账号密码登陆");
        try {
            TokenParameter tokenParameter = new TokenParameter();
            tokenParameter.getArgs()
                    .set("userType", passwordLoginDto.getUserType())
                    .set("account", passwordLoginDto.getAccount())
                    .set("password", passwordLoginDto.getPassword())
                    .set("wechatCode", passwordLoginDto.getWechatCode());

            ITokenGranter granter = TokenGranterBuilder.getGranter(GrantType.PASSWORD);
            return granter.grant(tokenParameter);

        } catch (Exception e) {
            log.error("账号密码登陆异常", e);
        }

        return R.fail("登陆失败");
    }

    @PostMapping("/refresh-token")
    @ApiOperation(value = "刷新令牌", notes = "刷新令牌")
    public R refreshToken(@ApiParam(value = "令牌") @NotBlank(message = "请输入令牌") @RequestParam(required = false) String refreshToken, @ApiParam(value = "用户类型") @NotBlank(message = "请选择用户类型") @RequestParam(required = false) UserType userType) {

        log.info("刷新token");
        try {
            TokenParameter tokenParameter = new TokenParameter();
            tokenParameter.getArgs()
                    .set("userType", userType)
                    .set("refreshToken", refreshToken);

            ITokenGranter granter = TokenGranterBuilder.getGranter(GrantType.REFRESHTOKEN);
            return granter.grant(tokenParameter);

        } catch (Exception e) {
            log.error("刷新令牌异常", e);
        }

        return R.fail("刷新令牌失败");
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    public R register(@Valid @RequestBody RegisterDto registerDto) {

        log.info("注册");
        try {
            TokenParameter tokenParameter = new TokenParameter();
            tokenParameter.getArgs()
                    .set("userType", registerDto.getUserType())
                    .set("mobile", registerDto.getMobile())
                    .set("password", registerDto.getPassword())
                    .set("smsCode", registerDto.getSmsCode());

            ITokenGranter granter = TokenGranterBuilder.getGranter(GrantType.REGISTER);
            return granter.grant(tokenParameter);

        } catch (Exception e) {
            log.error("注册异常", e);
        }

        return R.fail("注册失败");
    }

    @GetMapping("/captcha")
    @ApiOperation(value = "查询图形验证码")
    public R captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 5, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
    }

    /**
     * 发送手机验证码
     * 后期要加接口限制
     *
     * @param sendCodeDto
     * @return R
     */
    @PostMapping("/send-code")
    @ApiOperation(value = "查询手机短信验证码")
    public R sendCode(@Valid @RequestBody SendCodeDto sendCodeDto) {
        return mobileTokenGranter.sendSmsCode(sendCodeDto.getMobile(), sendCodeDto.getCodeType(), sendCodeDto.getUserType());
    }

}
