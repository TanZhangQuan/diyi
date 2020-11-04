package com.lgyun.system.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 *
 * 收获地址管理---添加或修改地址
 *
 * @date 2020/10/20.
 * @time 17:40.
 */
@Data
public class AddressDTO {


    /**
     * 收件人
     */
    @NotBlank(message = "请输入收件人")
    private String addressName;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String addressPhone;

    /**
     * 省
     */
    @NotBlank(message = "请输入正确的省")
    private String province;

    /**
     * 市
     */
    @NotBlank(message = "请输入正确的市")
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    @NotBlank(message = "请输入详细地址")
    private String detailedAddress;

    /**
     * 是否默认
     */
    @NotBlank(message = "是否默认")
    private Integer isDefault;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
