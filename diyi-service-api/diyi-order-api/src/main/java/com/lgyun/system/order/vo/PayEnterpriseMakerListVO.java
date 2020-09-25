package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "PayEnterpriseMakerListVO对象", description = "PayEnterpriseMakerListVO对象")
public class PayEnterpriseMakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 是否实名
     */
    private Boolean boolRealNameVerify;

    /**
     * 是否已签协议
     */
    private Boolean boolSign;

}
