package com.lgyun.system.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class IndividualYearMonthVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 月度开票金额
     */
    private BigDecimal monthMoney;

    /**
     * 年度开票金额
     */
    private BigDecimal yearMoney;

}
