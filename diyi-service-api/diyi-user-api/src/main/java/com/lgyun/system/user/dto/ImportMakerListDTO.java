package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class ImportMakerListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客姓名")
    @NotBlank(message = "请输入创客姓名")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank(message = "请输入身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "请输入手机号码")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "支行")
    private String subBankName;

    @ApiModelProperty(value = "卡号")
    private String bankCardNo;

}
