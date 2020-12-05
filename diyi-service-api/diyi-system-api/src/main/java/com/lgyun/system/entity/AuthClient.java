package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统客户端 实体类
 *
 * @author tzq
 * @since 2019-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_client")
public class AuthClient extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
