package com.lgyun.system.order.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "QueryEnterpriseAddressListEnterpriseVO对象", description = "QueryEnterpriseAddressListEnterpriseVO对象")
public class QueryEnterpriseAddressListEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //收货地址ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long addressId;

    //电话/手机
    private String addressName;

    //电话/手机
    private String addressPhone;

    //省
    private String province;

    //市
    private String city;

    //区
    private String area;

    //详细地址
    private String detailedAddress;

    //是否默认
    private Boolean isDefault;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
