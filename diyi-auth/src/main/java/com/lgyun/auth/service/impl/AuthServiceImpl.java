package com.lgyun.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.dto.*;
import com.lgyun.auth.service.IAuthService;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.auth.utils.WechatUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.constant.TokenConstant;
import com.lgyun.common.enumeration.CodeType;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.tool.*;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
    public R wechatlogin(WechatLoginDTO wechatLoginDto) throws Exception {

        //微信授权
        R<JSONObject> result = wechatUtil.authorization(wechatLoginDto.getWechatCode(), wechatLoginDto.getIv(), wechatLoginDto.getEncryptedData());
        if (!(result.isSuccess())) {
            return result;
        }
        JSONObject jsonObject = result.getData();
        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("sessionKey");
        String purePhoneNumber = jsonObject.getString("purePhoneNumber");

        R<String> res;
        switch (wechatLoginDto.getUserType()) {

            case MAKER:

                // 创客处理
                res = userClient.makerDeal(openid, sessionKey, purePhoneNumber, "", GrantType.WECHAT);
                if (!(res.isSuccess())) {
                    return res;
                }

                break;

            case PARTNER:

                // 合伙人处理
                res = userClient.partnerDeal(openid, sessionKey, purePhoneNumber, "", GrantType.WECHAT);
                if (!(res.isSuccess())) {
                    return res;
                }

                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.queryUserInfoByPhone(purePhoneNumber, wechatLoginDto.getUserType());
        if (userInfo == null) {
            return R.fail("登录失败");
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        return R.data(authInfo, "登录成功");
    }

    @Override
    public R<String> sendCode(SendCodeDTO sendCodeDto) {

        //验证码类型
        CodeType codeType = sendCodeDto.getCodeType();
        //用户类型
        UserType userType = sendCodeDto.getUserType();
        //手机号
        String mobile = sendCodeDto.getMobile();

        if (CodeType.LOGIN.equals(codeType) || CodeType.UPDATEPASSWORD.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (userClient.queryAdminCountByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case MAKER:
                    if (userClient.queryMakerCountByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case ENTERPRISE:
                    if (userClient.queryEnterpriseWorkerCountByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case SERVICEPROVIDER:
                    if (userClient.queryServiceProviderWorkerCountByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                case AGENTMAIN:
                    if (userClient.queryAgentMainWorkerCountByPhoneNumber(mobile) <= 0) {
                        return R.fail("手机号未注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else if (CodeType.REGISTER.equals(codeType)) {

            switch (userType) {

                case ADMIN:
                    if (userClient.queryAdminCountByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case MAKER:
                    if (userClient.queryMakerCountByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case ENTERPRISE:
                    if (userClient.queryEnterpriseWorkerCountByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case SERVICEPROVIDER:
                    if (userClient.queryServiceProviderWorkerCountByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                case AGENTMAIN:
                    if (userClient.queryAgentMainWorkerCountByPhoneNumber(mobile) > 0) {
                        return R.fail("手机号已注册");
                    }
                    break;

                default:
                    return R.fail("用户类型有误");
            }

        } else if (CodeType.UPDATEPHONE.equals(codeType)) {
            return smsUtil.sendCode(mobile, userType, codeType);
        } else {
            return R.fail("验证码类型有误");
        }

        return smsUtil.sendCode(mobile, userType, codeType);
    }

    @Override
    public R mobileLogin(MobileLoginDTO mobileLoginDto) throws Exception {

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
        String wechatCode;
        R<JSONObject> result;
        JSONObject jsonObject;
        String openid;
        String sessionKey;
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
                wechatCode = mobileLoginDto.getWechatCode();
                if (StringUtils.isBlank(wechatCode)) {
                    return R.fail("请输入微信授权码");
                }
                // 微信授权
                result = wechatUtil.authorization(wechatCode);
                if (!(result.isSuccess())) {
                    return result;
                }
                jsonObject = result.getData();
                openid = jsonObject.getString("openid");
                sessionKey = jsonObject.getString("sessionKey");
                // 创客处理
                res = userClient.makerDeal(openid, sessionKey, mobile, "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case PARTNER:
                // 微信授权码
                wechatCode = mobileLoginDto.getWechatCode();
                if (StringUtils.isBlank(wechatCode)) {
                    return R.fail("请输入微信授权码");
                }
                // 微信授权
                result = wechatUtil.authorization(wechatCode);
                if (!(result.isSuccess())) {
                    return result;
                }
                jsonObject = result.getData();
                openid = jsonObject.getString("openid");
                sessionKey = jsonObject.getString("sessionKey");
                // 合伙人处理
                res = userClient.partnerDeal(openid, sessionKey, mobile, "", GrantType.MOBILE);
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

            case AGENTMAIN:
                // 服务商处理
                res = userClient.agentMainWorkerDeal(mobile, "", "", GrantType.MOBILE);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.queryUserInfoByPhone(mobile, userType);
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
    public R passwordLogin(PasswordLoginDTO passwordLoginDto) throws Exception {

        //账号
        String account = passwordLoginDto.getAccount();
        //密码
        String password = passwordLoginDto.getPassword();
        //加密密码
        String encrypt = DigestUtil.encrypt(password);
        //用户类型
        UserType userType = passwordLoginDto.getUserType();

        R<String> res;
        String wechatCode;
        R<JSONObject> result;
        JSONObject jsonObject;
        String openid;
        String sessionKey;
        switch (userType) {

            case ADMIN:
                //管理员处理
                res = userClient.adminDeal("", account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case MAKER:
                // 查询微信授权码
                wechatCode = passwordLoginDto.getWechatCode();
                if (StringUtils.isBlank(wechatCode)) {
                    return R.fail("请输入微信授权码");
                }

                //微信授权
                result = wechatUtil.authorization(wechatCode);
                if (!(result.isSuccess())) {
                    return result;
                }
                jsonObject = result.getData();
                openid = jsonObject.getString("openid");
                sessionKey = jsonObject.getString("sessionKey");

                //创客处理
                res = userClient.makerDeal(openid, sessionKey, account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case PARTNER:
                // 查询微信授权码
                wechatCode = passwordLoginDto.getWechatCode();
                if (StringUtils.isBlank(wechatCode)) {
                    return R.fail("请输入微信授权码");
                }

                //微信授权
                result = wechatUtil.authorization(wechatCode);
                if (!(result.isSuccess())) {
                    return result;
                }
                jsonObject = result.getData();
                openid = jsonObject.getString("openid");
                sessionKey = jsonObject.getString("sessionKey");

                //创客处理
                res = userClient.partnerDeal(openid, sessionKey, account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case ENTERPRISE:
                //商户员工处理
                res = userClient.enterpriseWorkerDeal("", account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case SERVICEPROVIDER:
                //服务商员工处理
                res = userClient.serviceProviderWorkerDeal("", account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case AGENTMAIN:
                //渠道商员工处理
                res = userClient.agentMainWorkerDeal("", account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case TAXBUREAU:
                //税务局处理
                res = userClient.relBureauDeal("", account, encrypt, RelBureauType.TAXBUREAU, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case MARKETSUPERVISION:
                //市场监督管理局处理
                res = userClient.relBureauDeal("", account, encrypt, RelBureauType.MARKETSUPERVISION, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case INDUSTRIALPARKS:
                //产业园区处理
                res = userClient.relBureauDeal("", account, encrypt, RelBureauType.INDUSTRIALPARKS, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            case PAYMENTAGENCY:
                //支付机构处理
                res = userClient.relBureauDeal("", account, encrypt, RelBureauType.PAYMENTAGENCY, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.queryUserInfoByAccount(account, userType);
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
                userInfo = userClient.queryUserInfoByUserId(Func.toLong(claims.get(TokenConstant.USER_ID)), userType);
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
    public R<String> register(RegisterDTO registerDto) {
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

        R<String> res;
        switch (userType) {

            case MAKER:

                // 创客处理
                res = userClient.makerDeal("", "", mobile, DigestUtil.encrypt(password), GrantType.REGISTER);
                if (!(res.isSuccess())) {
                    return res;
                }

                //删除缓存短信验证码
                redisUtil.del(key);

                return R.success("注册成功");

            case PARTNER:

                // 合伙人处理
                res = userClient.partnerDeal("", "", mobile, DigestUtil.encrypt(password), GrantType.REGISTER);
                if (!(res.isSuccess())) {
                    return res;
                }

                //删除缓存短信验证码
                redisUtil.del(key);

                return R.success("注册成功");

            default:
                return R.fail("用户类型有误");
        }

    }

    @Override
    public R<String> updatePassword(UpdatePasswordDTO updatePasswordDto) {
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

            case PARTNER:
                //合伙人处理
                res = userClient.partnerDeal("", "", mobile, newPassword, GrantType.UPDATEPASSWORD);
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

            case AGENTMAIN:
                //服务商处理
                res = userClient.agentMainWorkerDeal(mobile, "", newPassword, GrantType.UPDATEPASSWORD);
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
