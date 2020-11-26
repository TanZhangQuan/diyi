package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/11/16.
 * @time 18:03.
 */
@Data
public class TimeoutPayMakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分包支付明细ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

}
