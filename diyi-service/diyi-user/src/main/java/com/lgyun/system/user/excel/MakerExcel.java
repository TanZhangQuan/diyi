package com.lgyun.system.user.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * UserDTO
 *
 * @author tzq
 * @since 2020/6/6 22:12
 */
@Data
public class MakerExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "姓名*")
    private String name;

    @Excel(name = "身份证号码*")
    private String idcardNo;

    @Excel(name = "手机号码*")
    private String phoneNumber;

    @Excel(name = "开户行")
    private String bankName;

    @Excel(name = "支行")
    private String subBankName;

    @Excel(name = "卡号")
    private String bankCardNo;

}
