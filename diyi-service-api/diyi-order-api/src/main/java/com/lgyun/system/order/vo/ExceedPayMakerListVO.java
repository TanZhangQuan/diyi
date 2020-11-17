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
public class ExceedPayMakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 交付支付验收单Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long acceptPaysheetId;
    /**
     * 支付明细id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;
    /**
     * 创客id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;
    /**
     * 天数
     */
    private Integer days;
}
