package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "身份证识别返回对象VO")
public class IdcardOcrVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "身份证正面图")
    private String idcardPic;

    @ApiModelProperty(value = "身份证反面图")
    private String idcardPicBack;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    private String idNo;

    @ApiModelProperty(value = "手持证件正面照")
    private String idcardHand;

    @ApiModelProperty(value = "手持证件反面照")
    private String idcardBackHand;

}
