package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "编辑创客DTO")
public class UpdatePartnerDeatilDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "政治面貌")
    private String politicState;

    @ApiModelProperty(value = "民族")
    private String nationality;

    @ApiModelProperty(value = "文化程度")
    private String levelofedu;

    @ApiModelProperty(value = "电子邮箱")
    private String emailAddress;

    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "开户银行")
    private String bankName;

    @ApiModelProperty(value = "开户支行")
    private String subBankName;

    @ApiModelProperty(value = "线上经营场所")
    private String runAddress;

    @ApiModelProperty(value = "线下经营地址")
    private String houseAddress;

    @ApiModelProperty(value = "住址")
    private String livingAddress;

    @ApiModelProperty(value = "自我描述")
    private String selfDesc;

}
