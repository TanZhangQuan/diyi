package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.RelBureauType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相关局名称")
    private String relBureauName;

    @ApiModelProperty(value = "相关局名称", notes = "com.lgyun.common.enumeration.RelBureauType")
    @NotNull(message = "请选择相关局类型")
    private RelBureauType relBureauType;

    @ApiModelProperty(value = "相关局创建开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty(value = "相关局创建结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
