package com.lgyun.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import com.lgyun.common.constant.TokenConstant;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * JwtUtil
 *
 * @author tzq
 * @since 2020/6/2 23:16
 */
public class JwtUtil {

	public static String SIGN_KEY = TokenConstant.SIGN_KEY;
	public static String BEARER = TokenConstant.BEARER;
	public static Integer AUTH_LENGTH = 7;

	public static String BASE64_SECURITY = Base64.getEncoder().encodeToString(SIGN_KEY.getBytes(StandardCharsets.UTF_8));

	/**
	 * 查询token串
	 *
	 * @param auth token
	 * @return String
	 */
	public static String getToken(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			if (headStr.compareTo(BEARER) == 0) {
				auth = auth.substring(7);
			}
			return auth;
		}
		return null;
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken token串
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			return Jwts.parser()
				.setSigningKey(Base64.getDecoder().decode(JwtUtil.BASE64_SECURITY))
				.parseClaimsJws(jsonWebToken).getBody();
		} catch (Exception ex) {
			return null;
		}
	}

}
