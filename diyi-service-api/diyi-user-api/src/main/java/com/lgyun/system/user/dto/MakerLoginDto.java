package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 创客登陆dto
 * @return
 * @date 2020.06.27
 */
@Data
public class MakerLoginDto implements Serializable {

    //微信授权乱码
    @NotBlank(message = "请输入微信授权乱码")
    private String random;

    //加密算法的初始向量
    @NotBlank(message = "请输入加密算法的初始向量")
    private String iv;

    //加密数据
    @NotBlank(message = "请输入加密数据")
    private String encryptedData;

}
