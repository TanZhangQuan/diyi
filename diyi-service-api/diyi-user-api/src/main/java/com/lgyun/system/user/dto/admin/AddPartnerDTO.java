package com.lgyun.system.user.dto.admin;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 平台--合伙人管理--添加合伙人DTO
 */

@Data
public class AddPartnerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 介绍合伙人ID
     */
    private Long introducePartnerID;

    /**
     * 合伙人姓名
     */
    @NotBlank(message = "合伙人姓名不能为空")
    private String name;

    /**
     * 身份证号码
     */
    @NotBlank(message = "合伙人身份证号码不能为空")
    private String idCardNo;

    /**
     * 手机号码
     */
    @NotBlank(message = "合伙人手机号码不能为空")
    @Length(min = 11, max = 11, message = "请输入11位的联系人1电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人1电话/手机")
    private String phoneNumber;

    /**
     * 银行卡号
     */
    @NotBlank(message = "合伙人银行卡号不能为空")
    private String bankCardNo;

    /**
     * 开户银行
     */
    @NotBlank(message = "合伙人开户银行不能为空")
    private String bankName;

    /**
     * 开户支行
     */
    @NotBlank(message = "合伙人开户支行不能为空")
    private String subBankName;

    /**
     * 登陆密码
     */
    @NotBlank(message = "请输入初始密码")
    private String loginPwd;
}
