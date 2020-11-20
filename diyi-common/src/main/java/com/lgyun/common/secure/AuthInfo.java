package com.lgyun.common.secure;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * AuthInfo
 *
 * @author tzq
 * @since 2020/6/5 19:00
 */
@Data
@ApiModel(description = "认证信息")
public class AuthInfo {

    @ApiModelProperty(value = "令牌")
    private String accessToken;

    @ApiModelProperty(value = "令牌类型")
    private String tokenType;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    @ApiModelProperty(value = "角色名")
    private String authority;

    @ApiModelProperty(value = "账号名")
    private String account;

    @ApiModelProperty(value = "过期时间")
    private long expiresIn;

}
