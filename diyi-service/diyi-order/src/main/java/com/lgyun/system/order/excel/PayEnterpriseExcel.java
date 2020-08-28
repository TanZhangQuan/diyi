package com.lgyun.system.order.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class PayEnterpriseExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ColumnWidth(50)
    @ExcelProperty("创客姓名")
    private String makerName;

    @ColumnWidth(50)
    @ExcelProperty("创客身份证号")
    private String makerIdCardNo;

    @ColumnWidth(50)
    @ExcelProperty("个体户名称")
    private String individualBusinessName;

    @ColumnWidth(50)
    @ExcelProperty("个体户统一社会信用代码")
    private String individualBusinessIbtaxNo;

    @ColumnWidth(15)
    @ExcelProperty("个体户年费")
    private BigDecimal individualBusinessAnnualFee;

    @ColumnWidth(50)
    @ExcelProperty("个独名称")
    private String individualEnterpriseName;

    @ColumnWidth(50)
    @ExcelProperty("个独统一社会信用代码")
    private String individualEnterpriseIbtaxNo;

    @ColumnWidth(15)
    @ExcelProperty("个独年费")
    private BigDecimal individualEnterpriseAnnualFee;

    @ColumnWidth(15)
    @ExcelProperty("创客到手")
    private BigDecimal makerNetIncome;

    @ColumnWidth(5)
    @ExcelProperty("服务税费率")
    private BigDecimal serviceRate;

    @ColumnWidth(15)
    @ExcelProperty("服务税费")
    private BigDecimal makerTaxFee;

    @ColumnWidth(15)
    @ExcelProperty("服务外包费")
    private BigDecimal makerNeIncome;

    @ColumnWidth(15)
    @ExcelProperty("创客首次\n" + "身份验证费")
    private BigDecimal auditFee;

    @ColumnWidth(15)
    @ExcelProperty("第三方\n" + "支付手续费")
    private BigDecimal payFee;

    @ColumnWidth(15)
    @ExcelProperty("价税合计\n" + "企业总支付额")
    private BigDecimal totalFee;

    @ColumnWidth(500)
    @ExcelProperty("备注")
    private String note;

}
