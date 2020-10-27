package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "税务局展示")
public class RelBureauVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("税务局编号")
    private Long bureauId;

    @ApiModelProperty("税务局名称")
    private String relBureauName;

    @ApiModelProperty("税务局联系人")
    private String contactPerson;

    @ApiModelProperty("税务局联系电话")
    private String telPhoneNo;

    @ApiModelProperty("税务局创建时间")
    private String createTime;
}
