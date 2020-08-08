package com.lgyun.system.order.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * UserDTO
 *
 * @author liangfeihu
 * @since 2020/6/6 22:12
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class InvoiceListExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ColumnWidth(10)
    @ExcelProperty("项目")
    private String projectName;

    @ColumnWidth(10)
    @ExcelProperty("数量")
    private String num;

    @ColumnWidth(20)
    @ExcelProperty("单位")
    private String company;

    @ColumnWidth(15)
    @ExcelProperty("单价")
    private String unitPrice;

    @ColumnWidth(20)
    @ExcelProperty("金额")
    private String amountMoney;

    @ColumnWidth(20)
    @ExcelProperty("税率")
    private String taxRate;

    @ColumnWidth(20)
    @ExcelProperty("价税合计")
    private String taxTotalprice;

    @ColumnWidth(20)
    @ExcelProperty("开票人姓名")
    private String invoicePeopleName;

    @ColumnWidth(20)
    @ExcelProperty("身份证号码")
    private String idCardNo;

    @ColumnWidth(20)
    @ExcelProperty("手机号码")
    private String phoneNumber;

    @ColumnWidth(20)
    @ExcelProperty("统一社会信用代码")
    private String ibtaxNo;

    @ColumnWidth(20)
    @ExcelProperty("公司名称")
    private String corporateName;

    @ExcelIgnore
    @ExcelProperty("对象")
    private SelfHelpInvoiceDto selfHelpInvoiceDto;
}