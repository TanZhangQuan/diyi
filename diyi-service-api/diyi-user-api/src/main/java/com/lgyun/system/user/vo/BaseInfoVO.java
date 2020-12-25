package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.CertificationState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class BaseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "认证状态")
    private CertificationState certificationState;

    @ApiModelProperty(value = "自我描述")
    private String selfDesc;

}
