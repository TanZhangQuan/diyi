package com.lgyun.common.secure;

import lombok.Data;

/**
 * TokenInfo
 *
 * @author tzq
 * @since 2020/6/5 19:00
 */
@Data
public class TokenInfo {

    /**
     * 令牌值
     */
    private String token;

    /**
     * 过期秒数
     */
    private int expire;

}
