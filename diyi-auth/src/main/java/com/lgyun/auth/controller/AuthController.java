package com.lgyun.auth.controller;

import com.lgyun.auth.granter.ITokenGranter;
import com.lgyun.auth.granter.MobileTokenGranter;
import com.lgyun.auth.granter.TokenGranterBuilder;
import com.lgyun.auth.granter.TokenParameter;
import com.lgyun.auth.utils.RedisUtil;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.cache.CacheNames;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.support.Kv;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.WebUtil;
import com.lgyun.system.user.entity.UserInfo;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证模块
 *
 * @author liangfeihu
 * @since 2020/6/6 01:04
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "用户授权认证")
public class AuthController {

    private RedisUtil redisUtil;
    private TokenUtil tokenUtil;
    private MobileTokenGranter mobileTokenGranter;

    @PostMapping("token")
    @ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
    public R<AuthInfo> token(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
                             @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
                             @ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId,
                             @ApiParam(value = "账号") @RequestParam(required = false) String account,
                             @ApiParam(value = "密码") @RequestParam(required = false) String password,
                             @ApiParam(value = "微信授权码") @RequestParam(required = false) String wechatCode,
                             @ApiParam(value = "加密算法的初始向量") @RequestParam(required = false) String iv,
                             @ApiParam(value = "加密数据") @RequestParam(required = false) String encryptedData) {

        String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

        TokenParameter tokenParameter = new TokenParameter();
        tokenParameter.getArgs()
                .set("grantType", grantType)
                .set("refreshToken", refreshToken)
                .set("tenantId", tenantId)
                .set("account", account)
                .set("password", password)
                .set("wechatCode", wechatCode)
                .set("iv", iv)
                .set("encryptedData", encryptedData)
                .set("userType", userType);

        ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
        UserInfo userInfo = granter.grant(tokenParameter);

        if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
            return R.fail(TokenUtil.USER_NOT_FOUND);
        }

        return R.data(tokenUtil.createAuthInfo(userInfo));
    }

    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码")
    public R<Kv> captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 30L, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
    }

    /**
     * 发送手机验证码
     * 后期要加接口限制
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @GetMapping("/mobile/{mobile}")
    public R<String> createCode(@PathVariable String mobile) {
        Assert.notNull(mobile, "手机号不能为空");
        return mobileTokenGranter.sendSmsCode(mobile);
    }

}
