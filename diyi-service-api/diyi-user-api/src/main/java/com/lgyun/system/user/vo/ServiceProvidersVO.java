package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/7/28.
 * @time 14:55.
 */
@Data
@ApiModel(value = "ServiceProvidersVO对象", description = "ServiceProvidersVO对象")
public class ServiceProvidersVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //服务商ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //服务商名称
    private String serviceProviderName;

}
