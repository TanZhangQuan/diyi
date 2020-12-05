package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class AddressUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收件地址ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 收件人
     */
    private String addressName;

    /**
     * 电话/手机
     */
    private String addressPhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 是否默认
     */
    private Boolean boolDefault;

}
