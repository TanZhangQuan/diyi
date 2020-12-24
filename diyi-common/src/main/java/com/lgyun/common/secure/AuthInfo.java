package com.lgyun.common.secure;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "JWT参数封装")
public class AuthInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "令牌")
    private String accessToken;

    @ApiModelProperty(value = "令牌类型")
    private String tokenType;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    @ApiModelProperty(value = "账号名")
    private String account;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "过期时间")
    private long expiresIn;

}
