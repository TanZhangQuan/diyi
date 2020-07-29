package com.lgyun.system.user.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

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
public class MakerExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ColumnWidth(10)
    @ExcelProperty("姓名")
    private String name;

    @ColumnWidth(20)
    @ExcelProperty("身份证号码")
    private String idcardNo;

    @ColumnWidth(15)
    @ExcelProperty("手机号码")
    private String phoneNumber;

    @ColumnWidth(20)
    @ExcelProperty("开户行")
    private String bankName;

    @ColumnWidth(20)
    @ExcelProperty("支行")
    private String subBankName;

    @ColumnWidth(20)
    @ExcelProperty("卡号")
    private String bankCardNo;

    @ExcelIgnore
    @ExcelProperty("商户ID")
    private Long enterpriseId;

}
