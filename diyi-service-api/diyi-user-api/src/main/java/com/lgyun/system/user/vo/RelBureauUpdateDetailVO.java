package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.RelBureauType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相关局ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "相关局类型")
    private RelBureauType relBureauType;

    @ApiModelProperty(value = "相关局名称")
    private String relBureauName;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "网址")
    private String relBureauWebsite;

    @ApiModelProperty(value = "联系人")
    private String contactName;

    @ApiModelProperty(value = "联系人职位")
    private String contactPosition;

    @ApiModelProperty(value = "联系手机号")
    private String contactPhone;

    @ApiModelProperty(value = "联系人邮箱")
    private String contactMail;

    @ApiModelProperty(value = "联系人手机号")
    private String contactWechat;

    @ApiModelProperty(value = "联系人微信")
    private String wechatNo;

    @ApiModelProperty(value = "局长姓名")
    private String directorName;

    @ApiModelProperty(value = "局长联系电话")
    private String directorPhone;

    @ApiModelProperty(value = "副局长姓名")
    private String viceDirectorName;

    @ApiModelProperty(value = "副局长联系电话")
    private String viceDirectorPhone;

    @ApiModelProperty(value = "用户名")
    private String relBureauUserName;

}
