package com.lgyun.common.secure;

import lombok.Data;

import java.io.Serializable;

/**
 * AuthInfo
 *
 * @author tzq
 * @since 2020/6/5 19:00
 */
@Data
public class AuthInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 令牌
     */
    private String accessToken;

    /**
     * 令牌类型
     */
    private String tokenType;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 账号名
     */
    private String account;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 过期时间
     */
    private long expiresIn;

}
