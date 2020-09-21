package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.auth.utils.WechatUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class WechatTokenGranter implements ITokenGranter {

    public static final String GRANT_TYPE = "WECHAT";

    private IUserClient userClient;
    private TokenUtil tokenUtil;
    private WechatUtil wechatUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R grant(TokenParameter tokenParameter) throws Exception {

        // 查询用户类型
        UserType userType = (UserType) tokenParameter.getArgs().get("userType");
        // 查询加密算法的初始向量
        String iv = tokenParameter.getArgs().getStr("iv");
        // 查询加密数据
        String encryptedData = tokenParameter.getArgs().getStr("encryptedData");
        //手机号
        String purePhoneNumber;
        switch (userType) {
            case MAKER:
                // 查询微信授权码
                String wechatCode = tokenParameter.getArgs().getStr("wechatCode");
                //微信授权
                R<JSONObject> result = wechatUtil.authorization(wechatCode, iv, encryptedData);
                if (!(result.isSuccess())){
                    return result;
                }
                JSONObject jsonObject = result.getData();
                String openid = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("sessionKey");
                purePhoneNumber = jsonObject.getString("purePhoneNumber");
                // 创客处理
                R res = userClient.makerDeal(openid, sessionKey, purePhoneNumber, "", GrantType.WECHAT);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.userInfoFindByPhoneAndUserType(purePhoneNumber, userType);
        if (userInfo == null) {
            return R.fail("登陆失败");
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        return R.data(authInfo);
    }

}