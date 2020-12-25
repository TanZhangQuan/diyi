package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class ContactInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "联系人1姓名")
    private String contact1Name;

    @ApiModelProperty(value = "联系人1职位")
    private String contact1Position;

    @ApiModelProperty(value = "联系人1电话/手机")
    private String contact1Phone;

    @ApiModelProperty(value = "联系人1邮箱")
    private String contact1Mail;

    @ApiModelProperty(value = "联系人2姓名")
    private String contact2Name;

    @ApiModelProperty(value = "联系人2职位")
    private String contact2Position;

    @ApiModelProperty(value = "联系人2电话/手机")
    private String contact2Phone;

    @ApiModelProperty(value = "联系人2邮箱")
    private String contact2Mail;

}
