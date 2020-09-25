package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/8/19.
 * @time 14:38.
 */
@Data
@ApiModel(value = "ApplicationVo对象", description = "ApplicationVo对象")
public class ApplicationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;
}
