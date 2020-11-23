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
    @NotBlank(message = "请输入创客姓名")
    private String name;

    /**
     * 身份证号码
     */
    @NotBlank(message = "请输入身份证号码")
    private String idcardNo;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
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
