package com.lgyun.system.user.vo.admin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xjw
 * @date 2020/10/21.
 * @time 14:36.
 */
@Data
public class AgentMainTransactionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 最近30天总包+分包流水
     */
    private BigDecimal totalSubMonthPay;

    /**
     * 最近365天总包+分包流水
     */
    private BigDecimal totalSubYearPay;

    /**
     * 总包+分包总流水
     */
    private BigDecimal totalSubAllPay;

    /**
     * 最近30天众包流水
     */
    private BigDecimal crowdMonthPay;

    /**
     * 最近365天众包流水
     */
    private BigDecimal crowdYearPay;

    /**
     * 众包总流水
     */
    private BigDecimal crowdAllPay;

    /**
     * 总创客数
     */
    private Integer makerNum;

    /**
     * 合作服务商数
     */
    private Integer serviceProviderNum;
}
