package com.lgyun.auth.granter;

import com.lgyun.auth.enums.BladeUserEnum;
import lombok.AllArgsConstructor;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author liangfeihu
 * @since 2020/6/6 01:02
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

    public static final String GRANT_TYPE = "password";

    private IUserClient userClient;

    @Override
    public UserInfo grant(TokenParameter tokenParameter) {
        String tenantId = tokenParameter.getArgs().getStr("tenantId");
        String account = tokenParameter.getArgs().getStr("account");
        String password = tokenParameter.getArgs().getStr("password");
        UserInfo userInfo = null;
        if (Func.isNoneBlank(account, password)) {
            // 获取用户类型
            String userType = tokenParameter.getArgs().getStr("userType");
            R<UserInfo> result;
            // 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
            if (userType.equals(BladeUserEnum.WEB.getName())) {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            } else if (userType.equals(BladeUserEnum.APP.getName())) {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            } else {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            }
            userInfo = result.isSuccess() ? result.getData() : null;
        }
        return userInfo;
    }

}
