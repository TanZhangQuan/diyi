package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相关局ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "相关局名称")
    private String relBureauName;

    @ApiModelProperty(value = "相关局联系人")
    private String contactName;

    @ApiModelProperty(value = "相关局联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "相关局账户状态")
    private AccountState relBureauState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
