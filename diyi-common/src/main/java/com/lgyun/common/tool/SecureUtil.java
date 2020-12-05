package com.lgyun.common.tool;

import com.lgyun.common.constant.TokenConstant;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Objects;

/**
 * Secure工具类
 *
 * @author tzq
 * @since 2020/6/5 18:57
 */
public class SecureUtil {
    private static final String BLADE_USER_REQUEST_ATTR = "_BLADE_USER_REQUEST_ATTR_";

    private final static String HEADER = TokenConstant.HEADER;
    private final static String BEARER = TokenConstant.BEARER;
    private final static String USER_TYPE = TokenConstant.USER_TYPE;
    private final static String USER_ID = TokenConstant.USER_ID;
    private final static Integer AUTH_LENGTH = TokenConstant.AUTH_LENGTH;
    private static String BASE64_SECURITY = Base64.getEncoder().encodeToString(TokenConstant.SIGN_KEY.getBytes(Charsets.UTF_8));

    /**
     * 查询用户信息
     *
     * @return BladeUser
     */
    public static BladeUser getUser() {
        HttpServletRequest request = WebUtil.getRequest();
        if (request == null) {
            return null;
        }
        // 优先从 request 中查询
        Object bladeUser = request.getAttribute(BLADE_USER_REQUEST_ATTR);
        if (bladeUser == null) {
            bladeUser = getUser(request);
            if (bladeUser != null) {
                // 设置到 request 中
                request.setAttribute(BLADE_USER_REQUEST_ATTR, bladeUser);
            }
        }
        return (BladeUser) bladeUser;
    }

    /**
     * 查询用户信息
     *
     * @param request request
     * @return BladeUser
     */
    public static BladeUser getUser(HttpServletRequest request) {
        Claims claims = getClaims(request);
        if (claims == null) {
            return null;
        }

        UserType userType = Enum.valueOf(UserType.class, (String) claims.get(SecureUtil.USER_TYPE));
        Long userId = Func.toLong(claims.get(SecureUtil.USER_ID));

        BladeUser bladeUser = new BladeUser();
        bladeUser.setUserType(userType);
        bladeUser.setUserId(userId);
        return bladeUser;
    }

    /**
     * 查询Claims
     *
     * @param request request
     * @return Claims
     */
    public static Claims getClaims(HttpServletRequest request) {
        String auth = request.getHeader(SecureUtil.HEADER);
        if (StringUtil.isNotBlank(auth) && auth.length() > AUTH_LENGTH) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(SecureUtil.BEARER) == 0) {
                auth = auth.substring(7);
                return SecureUtil.parseJWT(auth);
            }
        } else {
            String parameter = request.getParameter(SecureUtil.HEADER);
            if (StringUtil.isNotBlank(parameter)) {
                return SecureUtil.parseJWT(parameter);
            }
        }
        return null;
    }

    /**
     * 查询请求头
     *
     * @return header
     */
    public static String getHeader() {
        return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
    }

    /**
     * 查询请求头
     *
     * @param request request
     * @return header
     */
    public static String getHeader(HttpServletRequest request) {
        return request.getHeader(HEADER);
    }

    /**
     * 解析jsonWebToken
     *
     * @param jsonWebToken jsonWebToken
     * @return Claims
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(BASE64_SECURITY))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

}
