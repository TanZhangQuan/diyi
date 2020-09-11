package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.auth.utils.WechatUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author liangfeihu
 * @since 2020/6/6 01:02
 */
@Slf4j
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

    public static final String GRANT_TYPE = "PASSWORD";

    private TokenUtil tokenUtil;
    private IUserClient userClient;
    private RedisUtil redisUtil;
    private WechatUtil wechatUtil;

    @Override
    public R grant(TokenParameter tokenParameter) throws Exception {
        //查询账号
        String account = tokenParameter.getArgs().getStr("account");
        //查询密码
        String password = tokenParameter.getArgs().getStr("password");
        //查询用户类型
        UserType userType = (UserType) tokenParameter.getArgs().get("userType");

        String encrypt = DigestUtil.encrypt(password);

        UserInfo userInfo;
        R<String> res;
        switch (userType) {
            case MAKER:
                // 查询微信授权码
                String wechatCode = tokenParameter.getArgs().getStr("wechatCode");
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

                userInfo = userClient.userInfoByPhone(account, userType);
                break;

            case ENTERPRISE:
                //商户处理
                res = userClient.enterpriseWorkerDeal(account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }

                userInfo = userClient.userInfoByPhone(account, userType);
                break;

            case SERVICEPROVIDER:
                //服务商处理
                res = userClient.serviceProviderWorkerDeal(account, encrypt, GrantType.PASSWORD);
                if (!(res.isSuccess())) {
                    return res;
                }

                userInfo = userClient.userInfoByPhone(account, userType);
                break;

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

                userInfo = userClient.userInfo(account, encrypt, userType);
                break;

            default:
                return R.fail("用户类型有误");
        }

        if (userInfo == null) {
            if (UserType.ADMIN.equals(userType)) {
                return R.fail("账号或密码错误");
            } else {
                return R.fail("登陆失败");
            }
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        return R.data(authInfo);
    }

}
