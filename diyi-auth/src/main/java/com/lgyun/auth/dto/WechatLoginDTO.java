package com.lgyun.auth.dto;

import com.lgyun.common.enumeration.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "微信授权登录DTO")
public class WechatLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户类型", notes = "com.lgyun.common.enumeration.UserType")
    @NotNull(message = "请选择用户类型")
    private UserType userType;

    @ApiModelProperty(value = "微信授权码")
    @NotBlank(message = "请输入微信授权码")
    private String wechatCode;

    @ApiModelProperty(value = "加密算法的初始向量")
    @NotBlank(message = "请输入加密算法的初始向量")
    private String iv;

    @ApiModelProperty(value = "加密数据")
    @NotBlank(message = "请输入加密数据")
    private String encryptedData;

}
