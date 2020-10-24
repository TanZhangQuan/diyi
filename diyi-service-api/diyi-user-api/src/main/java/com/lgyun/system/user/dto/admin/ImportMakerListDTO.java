package com.lgyun.system.user.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "创客信息")
public class ImportMakerListDTO {

    @ApiModelProperty(value = "创客姓名", notes = "创客姓名")
    @NotBlank(message = "创客姓名不能为空！")
    private String name;

    @ApiModelProperty(value = "身份证号码", notes = "身份证号")
    @NotBlank(message = "身份证号码不能为空！")
    private String idcardNo;

    @ApiModelProperty(value = "手机号码", notes = "手机号码")
    @NotBlank(message = "手机号码不能为空！")
    private String phoneNumber;

    @ApiModelProperty(value = "开户行", notes = "开户行")
    private String bankName;

    @ApiModelProperty(value = "支行", notes = "支行")
    private String subBankName;

    @ApiModelProperty(value = "卡号", notes = "卡号")
    private String bankCardNo;

}
