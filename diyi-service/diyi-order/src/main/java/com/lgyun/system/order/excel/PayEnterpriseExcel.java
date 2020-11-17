package com.lgyun.system.order.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayEnterpriseExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("创客姓名")
    private String makerName;

    @ExcelProperty("创客身份证号")
    private String makerIdcardNo;

    @ExcelProperty("个体户名称")
    private String individualBusinessName;

    @ExcelProperty("个体户统一社会信用代码")
    private String individualBusinessIbtaxNo;

    @ExcelProperty("个体户年费")
    private BigDecimal individualBusinessAnnualFee;

    @ExcelProperty("个独名称")
    private String individualEnterpriseName;

    @ExcelProperty("个独统一社会信用代码")
    private String individualEnterpriseIbtaxNo;

    @ExcelProperty("个独年费")
    private BigDecimal individualEnterpriseAnnualFee;

    @ExcelProperty("创客到手")
    private BigDecimal makerNetIncome;

    @ExcelProperty("服务税费率")
    private BigDecimal serviceRate;

    @ExcelProperty("服务税费")
    private BigDecimal makerTaxFee;

    @ExcelProperty("服务外包费")
    private BigDecimal makerNeIncome;

    @ExcelProperty("创客首次身份验证费")
    private BigDecimal auditFee;

    @ExcelProperty("第三方支付手续费")
    private BigDecimal payFee;

    @ExcelProperty("价税合计企业总支付额")
    private BigDecimal totalFee;

    @ExcelProperty("备注")
    private String note;

}
