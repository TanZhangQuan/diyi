package com.lgyun.system.order.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lgyun.system.order.dto.SelfHelpInvoiceDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * UserDTO
 *
 * @author liangfeihu
 * @since 2020/6/6 22:12
 */
@Data
public class InvoiceListExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("项目")
    private String projectName;

    @ExcelProperty("数量")
    private String num;

    @ExcelProperty("单位")
    private String company;

    @ExcelProperty("单价")
    private String unitPrice;

    @ExcelProperty("金额")
    private String amountMoney;

    @ExcelProperty("税率")
    private String taxRate;

    @ExcelProperty("价税合计")
    private String taxTotalprice;

    @ExcelProperty("开票人姓名")
    private String invoicePeopleName;

    @ExcelProperty("身份证号码")
    private String idcardNo;

    @ExcelProperty("手机号码")
    private String phoneNumber;

    @ExcelProperty("统一社会信用代码")
    private String ibtaxNo;

    @ExcelProperty("公司名称")
    private String corporateName;

    @ExcelProperty("图片")
    private String tupian;

    @ExcelIgnore
    @ExcelProperty("对象")
    private SelfHelpInvoiceDTO selfHelpInvoiceDto;
}
