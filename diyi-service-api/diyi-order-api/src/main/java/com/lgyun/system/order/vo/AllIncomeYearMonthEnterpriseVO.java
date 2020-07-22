package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "AllIncomeYearMonthEnterpriseVO对象", description = "AllIncomeYearMonthEnterpriseVO对象")
public class AllIncomeYearMonthEnterpriseVO implements Serializable {

    //工单类型
    private String worksheetType;

    //工单创客类型
    private String makerType;

    //商户ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    //商户名称
    private String enterpriseName;

    //创客收入
    private BigDecimal income;

}
