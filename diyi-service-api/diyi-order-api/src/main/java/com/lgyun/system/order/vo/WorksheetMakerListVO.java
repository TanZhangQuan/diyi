package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2020/7/27.
 * @time 15:53.
 */
@Data
public class WorksheetMakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 身份证
     */
    private String idcardNo;

    /**
     * 验收金额
     */
    private BigDecimal checkMoney;
}
