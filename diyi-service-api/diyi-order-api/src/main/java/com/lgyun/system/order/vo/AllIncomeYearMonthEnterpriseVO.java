package com.lgyun.system.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class AllIncomeYearMonthEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 创客收入
     */
    private BigDecimal income;

}
