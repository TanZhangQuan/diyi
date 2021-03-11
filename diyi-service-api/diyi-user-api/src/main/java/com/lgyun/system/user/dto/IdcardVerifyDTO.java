package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "身份证认证DTO")
public class IdcardVerifyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "身份证正面图")
    @NotBlank(message = "请上传身份证正面图")
    private String idcardPic;

    @ApiModelProperty(value = "身份证反面图")
    @NotBlank(message = "请上传身份证反面图")
    private String idcardPicBack;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "请输入姓名")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank(message = "请输入身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "手持证件正面照")
//    @NotBlank(message = "请上传手持证件正面照")
    private String idcardHand;

    @ApiModelProperty(value = "手持证件反面照")
//    @NotBlank(message = "请上传手持证件反面照")
    private String idcardBackHand;

}
