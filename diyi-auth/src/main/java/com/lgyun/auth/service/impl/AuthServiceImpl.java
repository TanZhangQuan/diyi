package com.lgyun.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.dto.*;
import com.lgyun.auth.service.IAuthService;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.auth.utils.WechatUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.cache.CacheNames;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.constant.TokenConstant;
import com.lgyun.common.enumeration.CodeType;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.support.Kv;
import com.lgyun.common.tool.*;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import com.wf.captcha.SpecCaptcha;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-08-05 10:43:29
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private IUserClient userClient;
    private TokenUtil tokenUtil;
    private RedisUtil redisUtil;
    private SmsUtil smsUtil;
    private WechatUtil wechatUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R wechatlogin(WechatLoginDto wechatLoginDto) throws Exception {

        if (wechatLoginDto.getUserType() == UserType.MAKER) {
            //微信授权
            R<JSONObject> result = wechatUtil.authorization(wechatLoginDto.getWechatCode(), wechatLoginDto.getIv(), wechatLoginDto.getEncryptedData());
            if (!(result.isSuccess())) {
                return result;
            }
            JSONObject jsonObject = result.getData();
            String openid = jsonObject.getString("openid");
            String sessionKey = jsonObject.getString("sessionKey");
            String purePhoneNumber = jsonObject.getString("purePhoneNumber");
            // 创客处理
            R<String> res = userClient.makerDeal(openid, sessionKey, purePhoneNumber, "", GrantType.WECHAT);
            if (!(res.isSuccess())) {
                return res;
            }

            UserInfo userInfo = userClient.userInfoFindByPhoneAndUserType(purePhoneNumber, wechatLoginDto.getUserType());
            if (userInfo == null) {
                return R.fail("登录失败");
            }

            //创建认证token
            AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

            return R.data(authInfo, "登录成功");
        } else {
            return R.fail("用户类型有误");
        }
    }

    @Override
    public R<String> sendCode(SendCodeDto sendCodeDto) {

        //验证码类型
        CodeType codeType = sendCodeDto.getCodeType();
        //用户类型
        UserType userType = sendCodeDto.getUserType();
        //手机号
        String mobile = sendCodeDto.getMobile();

        if (CodeType.LOGIN.equals(codeType) || CodeType.UPDATEPASSWORD.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (userClient.adminCountFindByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case MAKER:
                    if (userClient.makerCountFindByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case ENTERPRISE:
                    if (userClient.enterpriseWorkerCountFindByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case SERVICEPROVIDER:
                    if (userClient.serviceProviderWorkerCountFindByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else if (CodeType.REGISTER.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (userClient.adminCountFindByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case MAKER:
                    if (userClient.makerCountFindByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case ENTERPRISE:
                    if (userClient.enterpriseWorkerCountFindByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case SERVICEPROVIDER:
                    if (userClient.serviceProviderWorkerCountFindByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else {
            return R.fail("验证码类型有误");
        }

        return smsUtil.sendCode(mobile, userType, codeType);
    }

    @Override
    public R mobileLogin(MobileLoginDto mobileLoginDto) throws Exception {

        //查询手机号
        String mobile = mobileLoginDto.getMobile();
        //查询缓存短信验证码
        String key = SmsConstant.AVAILABLE_TIME + mobile + "_" + mobileLoginDto.getUserType() + "_" + CodeType.LOGIN;
        String redisCode = (String) redisUtil.get(key);
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, mobileLoginDto.getSmsCode())) {
            return R.fail(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
        }

        //查询用户类型
        UserType userType = mobileLoginDto.getUserType();
        R<String> res;
        switch (userType) {

            case ADMIN:
                // 管理员处理
                res = userClient.adminDeal(mobile, "", "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case MAKER:
                // 微信授权码
                String wechatCode = mobileLoginDto.getWechatCode();
                if (StringUtils.isBlank(wechatCode)) {
                    return R.fail("请输入微信授权码");
                }
                // 微信授权
                R<JSONObject> result = wechatUtil.authorization(wechatCode);
                if (!(result.isSuccess())) {
                    return result;
                }
                JSONObject jsonObject = result.getData();
                String openid = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("sessionKey");
                // 创客处理
                res = userClient.makerDeal(openid, sessionKey, mobile, "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case ENTERPRISE:
                // 商户处理
                res = userClient.enterpriseWorkerDeal(mobile, "", "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case SERVICEPROVIDER:
                // 服务商处理
                res = userClient.serviceProviderWorkerDeal(mobile, "", "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.userInfoFindByPhoneAndUserType(mobile, userType);
        if (userInfo == null) {
            return R.fail("手机号未注册");
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);
        //删除缓存短信验证码
        redisUtil.del(key);

        return R.data(authInfo);
    }

    @Override
    public R passwordLogin(PasswordLoginDto passwordLoginDto) throws Exception {

        //账号
        String account = passwordLoginDto.getAccount();
        //密码
        String password = passwordLoginDto.getPassword();
        //加密密码
        String encrypt = DigestUtil.encrypt(password);
        //用户类型
        UserType userType = passwordLoginDto.getUserType();

        R<String> res;
        switch (userType) {

            case ADMIN:
                //判断是否跑图形验证码
//                if (CommonConstant.BOOL_GRAPH_CODE) {
//                    HttpServletRequest request = WebUtil.getRequest();
//                    String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
//                    String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
//                    // 查询验证码
//                    String redisCode = String.valueOf(redisUtil.get(CacheNames.CAPTCHA_KEY + key));
//                    // 判断验证码
//                    if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
//                        return R.fail(TokenUtil.CAPTCHA_NOT_CORRECT);
//                    }
//                }

                //管理员处理
                res = userClient.adminDeal("", account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case MAKER:
                // 查询微信授权码
                String wechatCode = passwordLoginDto.getWechatCode();
                if (StringUtils.isBlank(wechatCode)) {
                    return R.fail("请输入微信授权码");
                }

                //微信授权
                R<JSONObject> result = wechatUtil.authorization(wechatCode);
                if (!(result.isSuccess())) {
                    return result;
                }
                JSONObject jsonObject = result.getData();
                String openid = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("sessionKey");

                //创客处理
                res = userClient.makerDeal(openid, sessionKey, account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case ENTERPRISE:
                //商户处理
                res = userClient.enterpriseWorkerDeal("", account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case SERVICEPROVIDER:
                //服务商处理
                res = userClient.serviceProviderWorkerDeal("", account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.userInfoByAccountAndUserType(account, userType);
        if (userInfo == null) {
            return R.fail("用户不存在");
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        return R.data(authInfo);
    }

    @Override
    public R<AuthInfo> refreshToken(String refreshToken, UserType userType) {
        UserInfo userInfo = null;
        if (Func.isNoneBlank(refreshToken)) {
            Claims claims = SecureUtil.parseJWT(refreshToken);
            String tokenType = Func.toStr(Objects.requireNonNull(claims).get(TokenConstant.TOKEN_TYPE));
            if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
                userInfo = userClient.userInfoFindByUserIdAndUserType(Func.toLong(claims.get(TokenConstant.USER_ID)), userType);
            }
        }

        if (userInfo == null) {
            return R.fail("刷新令牌失败");
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        return R.data(authInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> register(RegisterDto registerDto) {
        //用户类型
        UserType userType = registerDto.getUserType();
        //手机号
        String mobile = registerDto.getMobile();
        //密码
        String password = registerDto.getPassword();
        //用户填写的短信验证码
        String smsCode = registerDto.getSmsCode();
        //缓存短信验证码
        String key = SmsConstant.AVAILABLE_TIME + mobile + "_" + registerDto.getUserType() + "_" + CodeType.REGISTER;
        String redisCode = (String) redisUtil.get(key);
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, smsCode)) {
            return R.fail(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
        }

        if (userType == UserType.MAKER) {
            // 创客处理
            R<String> res = userClient.makerDeal("", "", mobile, DigestUtil.encrypt(password), GrantType.REGISTER);
            if (!(res.isSuccess())) {
                return res;
            }

            //删除缓存短信验证码
            redisUtil.del(key);

            return R.success("注册成功");
        } else {
            return R.fail("用户类型有误");
        }
    }

    @Override
    public R<Kv> captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 5, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return R.data(Kv.init().set("key", key).set("image", specCaptcha.toBase64()));
    }

    @Override
    public R<String> updatePassword(UpdatePasswordDto updatePasswordDto) {
        //用户类型
        UserType userType = updatePasswordDto.getUserType();
        //手机号
        String mobile = updatePasswordDto.getPhoneNumber();
        //密码
        String newPassword = DigestUtil.encrypt(updatePasswordDto.getNewPassword());
        //用户填写的短信验证码
        String smsCode = updatePasswordDto.getSmsCode();
        //缓存短信验证码
        String key = SmsConstant.AVAILABLE_TIME + mobile + "_" + updatePasswordDto.getUserType() + "_" + CodeType.UPDATEPASSWORD;
        String redisCode = (String) redisUtil.get(key);
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, smsCode)) {
            return R.fail(TokenUtil.SMS_CAPTCHA_NOT_CORRECT);
        }

        R<String> res;
        switch (userType) {

            case ADMIN:
                //管理员处理
                res = userClient.adminDeal(mobile, "", newPassword, GrantType.UPDATEPASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case MAKER:
                //创客处理
                res = userClient.makerDeal("", "", mobile, newPassword, GrantType.UPDATEPASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case ENTERPRISE:
                //商户处理
                res = userClient.enterpriseWorkerDeal(mobile, "", newPassword, GrantType.UPDATEPASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case SERVICEPROVIDER:
                //服务商处理
                res = userClient.serviceProviderWorkerDeal(mobile, "", newPassword, GrantType.UPDATEPASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        //删除缓存短信验证码
        redisUtil.del(key);

        return R.success("修改密码成功");
    }
}
