package com.lgyun.system.user.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * UserDTO
 *
 * @author tzq
 * @since 2020/6/6 22:12
 */
@Data
public class UserExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("租户编号")
    private String tenantId;

    @ExcelProperty("账户")
    private String account;

    @ColumnWidth(10)
    @ExcelProperty("昵称")
    private String name;

    @ColumnWidth(10)
    @ExcelProperty("姓名")
    private String realName;

    @ExcelProperty("邮箱")
    private String email;

    @ColumnWidth(15)
    @ExcelProperty("手机")
    private String phone;

    @ExcelIgnore
    @ExcelProperty("角色ID")
    private String roleId;

    @ExcelIgnore
    @ExcelProperty("部门ID")
    private String deptId;

    @ExcelIgnore
    @ExcelProperty("岗位ID")
    private String postId;

    @ExcelProperty("角色名称")
    private String roleName;

    @ExcelProperty("部门名称")
    private String deptName;

    @ExcelProperty("岗位名称")
    private String postName;

    @ColumnWidth(20)
    @ExcelProperty("生日")
    private Date birthday;

}
