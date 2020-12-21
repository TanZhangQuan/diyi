package com.lgyun.system.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ImportMakerListDTO {

    /**
     * 创客姓名
     */
    @NotBlank(message = "请输入创客姓名")
    private String name;

    /**
     * 身份证号码
     */
    @NotBlank(message = "请输入身份证号码")
    private String idcardNo;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 支行
     */
    private String subBankName;

    /**
     * 卡号
     */
    private String bankCardNo;

}
