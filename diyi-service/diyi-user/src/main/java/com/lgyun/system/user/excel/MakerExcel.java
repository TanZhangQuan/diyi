package com.lgyun.system.user.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MakerExcel
 *
 * @author tzq
 * @since 2020/6/6 22:12
 */
@Data
public class MakerExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("身份证号码")
    private String idcardNo;

    @ExcelProperty("手机号码")
    private String phoneNumber;

    @ExcelProperty("开户行")
    private String bankName;

    @ExcelProperty("支行")
    private String subBankName;

    @ExcelProperty("卡号")
    private String bankCardNo;

}
