package com.lgyun.auth.client;

import java.io.Serializable;

/**
 * 多终端详情接口
 *
 * @author tzq
 * @since 2020/6/5 19:04
 */
public interface IClientDetails extends Serializable {

    /**
     * 客户端id.
     *
     * @return String.
     */
    String getClientId();

    /**
     * 客户端密钥.
     *
     * @return String.
     */
    String getClientSecret();

    /**
     * 客户端token过期时间
     *
     * @return Integer
     */
    Integer getAccessTokenValidity();

    /**
     * 客户端刷新token过期时间
     *
     * @return Integer
     */
    Integer getRefreshTokenValidity();

}
