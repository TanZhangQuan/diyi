package com.lgyun.auth.client;

import lombok.Data;

/**
 * 客户端详情
 *
 * @author tzq
 * @since 2020/6/5 19:04
 */
@Data
public class ClientDetails implements IClientDetails {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 令牌过期秒数
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌过期秒数
     */
    private Integer refreshTokenValidity;

}
