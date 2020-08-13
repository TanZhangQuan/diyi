package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "PayEnterpriseStatisticalVO对象", description = "PayEnterpriseStatisticalVO对象")
public class EnterpriseStatisticalVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //最近30天支出
    private BigDecimal monthPay;

    //最近365天支出
    private BigDecimal yearPay;

    //总支出
    private BigDecimal allPay;

    //总创客数
    private BigDecimal makerNum;

    //合作服务商数
    private BigDecimal serviceProviderNum;
}
