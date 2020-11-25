package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 接收商户的联系人信息
 */
@Data
public class ContactsInfoDTO {
    /**
     * 联系人1姓名
     */
    @NotBlank(message = "请输入联系人1姓名")
    private String contact1Name;

    /**
     * 联系人1职位
     */
    @NotNull(message = "请选择联系人1职位")
    private PositionName contact1Position;

    /**
     * 联系人1电话/手机
     */
    @NotBlank(message = "请输入联系人1手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String contact1Phone;

    /**
     * 联系人1邮箱
     */
    @NotBlank(message = "请输入联系人1邮箱")
    @Email(message = "请输入正确的联系人1邮箱")
    private String contact1Mail;

    /**
     * 联系人2姓名
     */
    @NotBlank(message = "请输入联系人2姓名")
    private String contact2Name;

    /**
     * 联系人2职位
     */
    @NotNull(message = "请选择联系人2职位")
    private PositionName contact2Position;

    /**
     * 联系人2电话/手机
     */
    @NotBlank(message = "请输入联系人2手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String contact2Phone;

    /**
     * 联系人2邮箱
     */
    @NotBlank(message = "请输入联系人2邮箱")
    @Email(message = "请输入正确的联系人2邮箱")
    private String contact2Mail;

}
