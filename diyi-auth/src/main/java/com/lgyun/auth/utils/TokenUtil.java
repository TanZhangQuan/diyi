package com.lgyun.auth.utils;

import com.lgyun.auth.client.IClientDetails;
import com.lgyun.auth.client.IClientDetailsService;
import com.lgyun.common.constant.SecureConstant;
import com.lgyun.common.constant.TokenConstant;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.secure.AuthInfo;
import com.lgyun.common.secure.TokenInfo;
import com.lgyun.common.tool.*;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

import static com.lgyun.common.constant.TokenConstant.CLIENT_ID;

/**
 * 认证工具类
 *
 * @author tzq
 * @since 2020/6/6 00:21
 */
@Component
public class TokenUtil {

    public final static String SMS_CAPTCHA_NOT_CORRECT = "短信验证码不正确";
    private static String BASE64_SECURITY = Base64.getEncoder().encodeToString(TokenConstant.SIGN_KEY.getBytes(Charsets.UTF_8));

    @Autowired
    private IClientDetailsService clientDetailsService;

    /**
     * 创建认证token
     *
     * @param userInfo 用户信息
     * @return token
     */
    public AuthInfo createAuthInfo(UserInfo userInfo) {
        User user = userInfo.getUser();

        //设置jwt参数
        Map<String, String> param = new HashMap<>(16);
        param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
        param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
        param.put(TokenConstant.ROLE_ID, user.getRoleId());
        param.put(TokenConstant.ACCOUNT, user.getAccount());
        param.put(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()));

        TokenInfo accessToken = this.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
        AuthInfo authInfo = new AuthInfo();
        authInfo.setAccount(user.getAccount());
        authInfo.setAuthority(Func.join(userInfo.getRoles()));
        authInfo.setAccessToken(accessToken.getToken());
        authInfo.setExpiresIn(accessToken.getExpire());
        authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
        authInfo.setTokenType(TokenConstant.BEARER);

        return authInfo;
    }

    /**
     * 创建refreshToken
     *
     * @param userInfo 用户信息
     * @return refreshToken
     */
    private TokenInfo createRefreshToken(UserInfo userInfo) {
        User user = userInfo.getUser();
        Map<String, String> param = new HashMap<>(16);
        param.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
        param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
        return this.createJWT(param, "audience", "issuser", TokenConstant.REFRESH_TOKEN);
    }

    /**
     * 查询客户端信息
     *
     * @param clientId 客户端id
     * @return clientDetails
     */
    private IClientDetails clientDetails(String clientId) {
        return clientDetailsService.loadClientByClientId(clientId);
    }

    /**
     * 校验Client
     *
     * @param clientId     客户端id
     * @param clientSecret 客户端密钥
     * @return boolean
     */
    private boolean validateClient(IClientDetails clientDetails, String clientId, String clientSecret) {
        if (clientDetails != null) {
            return StringUtil.equals(clientId, clientDetails.getClientId()) && StringUtil.equals(clientSecret, clientDetails.getClientSecret());
        }
        return false;
    }

    /**
     * 创建令牌
     *
     * @param user      user
     * @param audience  audience
     * @param issuer    issuer
     * @param tokenType tokenType
     * @return jwt
     */
    public TokenInfo createJWT(Map<String, String> user, String audience, String issuer, String tokenType) {

        String[] tokens = extractAndDecodeHeader();
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        // 查询客户端信息
        IClientDetails clientDetails = clientDetails(clientId);

        // 校验客户端信息
        if (!validateClient(clientDetails, clientId, clientSecret)) {
            throw new CustomException("客户端认证失败!");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的类
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JsonWebToken")
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);

        //设置JWT参数
        user.forEach(builder::claim);

        //设置应用id
        builder.claim(CLIENT_ID, clientId);

        //添加Token过期时间
        long expireMillis;
        if (tokenType.equals(TokenConstant.ACCESS_TOKEN)) {
            expireMillis = clientDetails.getAccessTokenValidity() * 1000;
        } else if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
            expireMillis = clientDetails.getRefreshTokenValidity() * 1000;
        } else {
            expireMillis = getExpire();
        }
        long expMillis = nowMillis + expireMillis;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now);

        // 组装Token信息
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(builder.compact());
        tokenInfo.setExpire((int) expireMillis / 1000);

        return tokenInfo;
    }

    /**
     * 客户端信息解码
     */
    @SneakyThrows
    public String[] extractAndDecodeHeader() {
        // 查询请求头客户端信息
        String header = Objects.requireNonNull(WebUtil.getRequest()).getHeader(SecureConstant.BASIC_HEADER_KEY);
        header = Func.toStr(header).replace(SecureConstant.BASIC_HEADER_PREFIX_EXT, SecureConstant.BASIC_HEADER_PREFIX);
        if (!header.startsWith(SecureConstant.BASIC_HEADER_PREFIX)) {
            throw new CustomException("No client information in request header");
        }
        byte[] base64Token = header.substring(6).getBytes(Charsets.UTF_8_NAME);

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new CustomException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, Charsets.UTF_8_NAME);
        int index = token.indexOf(StringPool.COLON);
        if (index == -1) {
            throw new CustomException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, index), token.substring(index + 1)};
        }
    }

    /**
     * 查询请求头中的客户端id
     */
    public String getClientIdFromHeader() {
        String[] tokens = extractAndDecodeHeader();
        assert tokens.length == 2;
        return tokens[0];
    }

    /**
     * 查询过期时间(次日凌晨3点)
     *
     * @return expire
     */
    public long getExpire() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 3);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() - System.currentTimeMillis();
    }

}
