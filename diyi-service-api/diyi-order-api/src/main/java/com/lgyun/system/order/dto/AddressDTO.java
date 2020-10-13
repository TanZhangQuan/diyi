package com.lgyun.system.order.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 地址
 *
 * @author tzq
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 地址ID
     */
    private Long addressId;

    /**
     * 收件人
     */
    @NotBlank(message = "请输入收件人")
    private String addressName;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String addressPhone;

    /**
     * 省
     */
    @NotBlank(message = "请选择省份")
    private String province;

    /**
     * 市
     */
    @NotBlank(message = "请选择市")
    private String city;

    /**
     * 区
     */
    @NotBlank(message = "请选择区")
    private String area;

    /**
     * 详细地址
     */
    @NotBlank(message = "请输入详细地址")
    private String detailedAddress;

    /**
     * 是否默认
     */
    @NotNull(message = "请选择是否默认")
    private Boolean isDefault;

}
