package com.lgyun.common.tool;

import com.lgyun.common.constant.RoleConstant;
import com.lgyun.common.constant.TokenConstant;
import com.lgyun.common.secure.BladeUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Objects;

/**
 * Secure工具类
 *
 * @author liangfeihu
 * @since 2020/6/5 18:57
 */
public class SecureUtil {
    private static final String BLADE_USER_REQUEST_ATTR = "_BLADE_USER_REQUEST_ATTR_";

    private final static String HEADER = TokenConstant.HEADER;
    private final static String BEARER = TokenConstant.BEARER;
    private final static String ACCOUNT = TokenConstant.ACCOUNT;
    private final static String USER_ID = TokenConstant.USER_ID;
    private final static String ROLE_ID = TokenConstant.ROLE_ID;
    private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
    private final static String TENANT_ID = TokenConstant.TENANT_ID;
    private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
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
        String clientId = Func.toStr(claims.get(SecureUtil.CLIENT_ID));
        Long userId = Func.toLong(claims.get(SecureUtil.USER_ID));
        String tenantId = Func.toStr(claims.get(SecureUtil.TENANT_ID));
        String roleId = Func.toStr(claims.get(SecureUtil.ROLE_ID));
        String account = Func.toStr(claims.get(SecureUtil.ACCOUNT));
        String roleName = Func.toStr(claims.get(SecureUtil.ROLE_NAME));

        BladeUser bladeUser = new BladeUser();
        bladeUser.setClientId(clientId);
        bladeUser.setUserId(userId);
        bladeUser.setTenantId(tenantId);
        bladeUser.setAccount(account);
        bladeUser.setRoleId(roleId);
        bladeUser.setRoleName(roleName);
        return bladeUser;
    }

    /**
     * 是否为超管
     *
     * @return boolean
     */
    public static boolean isAdministrator() {
        return StringUtil.containsAny(getUserRole(), RoleConstant.ADMIN);
    }

    /**
     * 查询用户id
     *
     * @return userId
     */
    public static Long getUserId() {
        BladeUser user = getUser();
        return (null == user) ? -1 : user.getUserId();
    }

    /**
     * 查询用户id
     *
     * @param request request
     * @return userId
     */
    public static Long getUserId(HttpServletRequest request) {
        BladeUser user = getUser(request);
        return (null == user) ? -1 : user.getUserId();
    }

    /**
     * 查询用户账号
     *
     * @return userAccount
     */
    public static String getUserAccount() {
        BladeUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getAccount();
    }

    /**
     * 查询用户账号
     *
     * @param request request
     * @return userAccount
     */
    public static String getUserAccount(HttpServletRequest request) {
        BladeUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getAccount();
    }

    /**
     * 查询用户角色
     *
     * @return userName
     */
    public static String getUserRole() {
        BladeUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getRoleName();
    }

    /**
     * 查询用角色
     *
     * @param request request
     * @return userName
     */
    public static String getUserRole(HttpServletRequest request) {
        BladeUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getRoleName();
    }

    /**
     * 查询租户ID
     *
     * @return tenantId
     */
    public static String getTenantId() {
        BladeUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getTenantId();
    }

    /**
     * 查询租户ID
     *
     * @param request request
     * @return tenantId
     */
    public static String getTenantId(HttpServletRequest request) {
        BladeUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getTenantId();
    }

    /**
     * 查询客户端id
     *
     * @return tenantId
     */
    public static String getClientId() {
        BladeUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getClientId();
    }

    /**
     * 查询客户端id
     *
     * @param request request
     * @return tenantId
     */
    public static String getClientId(HttpServletRequest request) {
        BladeUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getClientId();
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
