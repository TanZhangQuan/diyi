package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class IncomeDetailYearMonthVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收入
     */
    private BigDecimal income;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 验收金额
     */
    private BigDecimal checkMoney;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date checkDate;

}
