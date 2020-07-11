package com.lgyun.auth.granter;

import com.lgyun.common.enumeration.GrantType;
import lombok.AllArgsConstructor;
import com.lgyun.common.exception.SecureException;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SpringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TokenGranterBuilder
 *
 * @author liangfeihu
 * @since 2020/6/6 01:01
 */
@AllArgsConstructor
public class TokenGranterBuilder {

	/**
	 * TokenGranter缓存池
	 */
	private static Map<String, ITokenGranter> granterPool = new ConcurrentHashMap<>();

	static {
		granterPool.put(PasswordTokenGranter.GRANT_TYPE, SpringUtil.getBean(PasswordTokenGranter.class));
		granterPool.put(RefreshTokenGranter.GRANT_TYPE, SpringUtil.getBean(RefreshTokenGranter.class));
		granterPool.put(MobileTokenGranter.GRANT_TYPE, SpringUtil.getBean(MobileTokenGranter.class));
		granterPool.put(WechatTokenGranter.GRANT_TYPE, SpringUtil.getBean(WechatTokenGranter.class));
		granterPool.put(RegisterGranter.GRANT_TYPE, SpringUtil.getBean(RegisterGranter.class));
	}

	/**
	 * 获取TokenGranter
	 *
	 * @param grantType 授权类型
	 * @return ITokenGranter
	 */
	public static ITokenGranter getGranter(GrantType grantType) {
		ITokenGranter tokenGranter = granterPool.get(Func.toStr(grantType.getValue(), PasswordTokenGranter.GRANT_TYPE));
		if (tokenGranter == null) {
			throw new SecureException("no grantType was found");
		} else {
			return tokenGranter;
		}
	}

}
