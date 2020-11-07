package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "导入创客参数")
public class ImportMakerListDTO {

    /**
     * 创客姓名
     */
    @NotBlank(message = "创客姓名不能为空")
    private String name;

    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证号码不能为空")
    private String idcardNo;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 支行
     */
    private String subBankName;

    /**
     * 卡号
     */
    private String bankCardNo;

}
