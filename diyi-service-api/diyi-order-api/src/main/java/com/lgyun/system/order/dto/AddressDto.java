package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 地址
 * @author jun.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class AddressDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 收件人
     */
    @NotBlank(message = "请输入身份证号码")
    private String addressName;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    private String addressPhone;

    /**
     * 省
     */
    @NotBlank(message = "请输入省")
    private String province;

    /**
     * 市
     */
    @NotBlank(message = "请输入市")
    private String city;

    /**
     * 区
     */
    @NotBlank(message = "请输入区")
    private String area;

    /**
     * 详细地址
     */
    @NotBlank(message = "请输入详细地址")
    private String detailedAddress;

    /**
     * 是否默认[0:默认,1:不默认]
     */
    private Integer isDefault;
}
