package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.Ibstate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "个体户或个独查询DTO")
public class IndividualBusinessEnterpriseListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个体户/个独名称")
    private String ibname;

    @ApiModelProperty(value = "个体户/个独状态", notes = "com.lgyun.common.enumeration.Ibstate")
    private Ibstate ibstate;

    @ApiModelProperty(value = "个体户/个独注册开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty(value = "个体户/个独注册结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
