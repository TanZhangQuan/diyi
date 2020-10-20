package com.lgyun.auth.dto;

import com.lgyun.common.enumeration.UserType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 微信授权登录DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class WechatLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户类型
     */
    @NotNull(message = "请选择用户类型")
    private UserType userType;

    /**
     * 微信授权码
     */
    @NotBlank(message = "请输入微信授权码")
    private String wechatCode;

    /**
     * 加密算法的初始向量
     */
    @NotBlank(message = "请输入加密算法的初始向量")
    private String iv;

    /**
     * 加密数据
     */
    @NotBlank(message = "请输入加密数据")
    private String encryptedData;

}
