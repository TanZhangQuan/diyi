package com.lgyun.system.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 添加创客DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class MakerAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名")
    private String name;

    /**
     * 身份证号码
     */
    @NotBlank(message = "请输入身份证号码")
    private String idcardNo;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 开户行支行
     */
    private String subBankName;

    /**
     * 新密码
     */
    private String password;

}
