package com.lgyun.auth.granter;

import com.lgyun.auth.utils.TokenUtil;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.TokenConstant;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SecureUtil;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.feign.IUserClient;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RefreshTokenGranter
 *
 * @author liangfeihu
 * @since 2020/6/6 01:01
 */
@Slf4j
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "REFRESHTOKEN";

	private IUserClient userClient;
	private TokenUtil tokenUtil;

	@Override
	public R grant(TokenParameter tokenParameter) {
		//查询用户类型
		UserType userType = (UserType) tokenParameter.getArgs().get("userType");
		String refreshToken = tokenParameter.getArgs().getStr("refreshToken");
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
}
