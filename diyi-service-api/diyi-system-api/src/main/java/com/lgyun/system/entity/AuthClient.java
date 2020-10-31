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
 * @author liangfeihu
 * @since 2019-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_client")
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
     * 资源集合
     */
    private String resourceIds;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 授权类型
     */
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    private String webServerRedirectUri;

    /**
     * 权限
     */
    private String authorities;

    /**
     * 令牌过期秒数
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌过期秒数
     */
    private Integer refreshTokenValidity;

    /**
     * 附加说明
     */
    private String additionalInformation;

    /**
     * 自动授权
     */
    private String autoapprove;

}
