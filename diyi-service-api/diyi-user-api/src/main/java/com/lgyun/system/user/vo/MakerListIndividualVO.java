package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class MakerListIndividualVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     *
     */
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    /**
     *
     */
    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    /**
     *
     */
    @ApiModelProperty(value = "身份证正面图")
    private String idcardPic;

    /**
     *
     */
    @ApiModelProperty(value = "身份证反面图")
    private String idcardPicBack;

    /**
     *
     */
    @ApiModelProperty(value = "手持证件正面照")
    private String idcardHand;

    /**
     *
     */
    @ApiModelProperty(value = "手持证件反面照")
    private String idcardBackHand;
}