package com.lgyun.auth.granter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.WechatConstant;
import com.lgyun.common.enumeration.GrantType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.tool.AesCbcUtil;
import com.lgyun.common.tool.HttpUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class WechatTokenGranter implements ITokenGranter {
    private static Logger logger = LoggerFactory.getLogger(WechatTokenGranter.class);

    public static final String GRANT_TYPE = "WECHAT";

    private IUserClient userClient;
    private TokenUtil tokenUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R grant(TokenParameter tokenParameter) throws Exception {

        // 获取用户类型
        UserType userType = (UserType) tokenParameter.getArgs().get("userType");
        //手机号
        String purePhoneNumber;
        switch (userType) {
            case MAKER:
                // 获取微信授权码
                String wechatCode = tokenParameter.getArgs().getStr("wechatCode");

                Map<String, String> requestUrlParam = new HashMap<>();
                requestUrlParam.put("grant_type", "authorization_code");    //默认参数
                requestUrlParam.put("appid", WechatConstant.WECHAT_APPID);    //开发者设置中的appId
                requestUrlParam.put("secret", WechatConstant.WECHAT_SECRET);    //开发者设置中的appSecret
                requestUrlParam.put("js_code", wechatCode);    //小程序调用wx.login返回的code

                JSONObject jsonObject = JSON.parseObject(HttpUtil.post(WechatConstant.WECHAT_SESSIONHOST, requestUrlParam));
                if (jsonObject == null) {
                    logger.error("微信授权失败, 获取数据失败");
                    return R.fail("登陆失败");
                }

                Object errcode = jsonObject.get("errcode");
                String errmsg = jsonObject.getString("errmsg");
                if (errcode != null) {
                    logger.error(errmsg);
                    return R.fail(errmsg);
                }

                String openid = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("session_key");
                if (StringUtils.isBlank(openid)) {
                    logger.error("微信授权失败, 返回参数缺失openid");
                    return R.fail("登陆失败");
                }

                if (StringUtils.isBlank(sessionKey)) {
                    logger.error("微信授权失败, 返回参数缺失session_key");
                    return R.fail("登陆失败");
                }

                // 获取加密算法的初始向量
                String iv = tokenParameter.getArgs().getStr("iv");
                // 获取加密数据
                String encryptedData = tokenParameter.getArgs().getStr("encryptedData");
                // 参数含义：第一个，加密数据串（String）；第二个，session_key需要通过微信小程序的code获得（String）；
                // 第三个，数据加密时所使用的偏移量，解密时需要使用（String）；第四个，编码
                String AesResult = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");

                if (StringUtil.isBlank(AesResult)) {
                    return R.fail("登陆失败");
                }

                // 将解密后的JSON格式字符串转化为对象
                jsonObject = JSONObject.parseObject(AesResult);
                // 获取手机号
                purePhoneNumber = jsonObject.getString("purePhoneNumber");
                // 创客处理
                R res = userClient.makerSaveOrUpdate(openid, sessionKey, purePhoneNumber, "", GrantType.WECHAT);
                if (!(res.isSuccess())) {
                    return res;
                }
                break;

            default:
                return R.fail("用户类型有误");
        }

        UserInfo userInfo = userClient.userInfoByPhone(purePhoneNumber, userType);
        if (userInfo == null) {
            return R.fail("登陆失败");
        }

        //创建认证token
        AuthInfo authInfo = tokenUtil.createAuthInfo(userInfo);

        return R.data(authInfo);
    }

}