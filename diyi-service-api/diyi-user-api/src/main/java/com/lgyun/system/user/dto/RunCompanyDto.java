package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/7/8.
 * @time 15:18.
 */
@Data
public class RunCompanyDto implements Serializable {

    /**
     * 公司名称
     */
    @NotBlank(message = "请输入公司名称")
    private String companyName;

    /**
     * 纳税人识别号
     */
    @NotBlank(message = "请输入纳税人识别号")
    private String taxNo;

    /**
     * 联系电话
     */
    private String phoneNo;


    /**
     * 公司地址
     */
    private String employeeName;

    /**
     * 银行账户名
     */
    private String bankName;

    /**
     * 银行账户
     */
    private String bankAccount;
}
