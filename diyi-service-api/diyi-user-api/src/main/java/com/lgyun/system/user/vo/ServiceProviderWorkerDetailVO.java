package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "服务商员工详情VO")
public class ServiceProviderWorkerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String workerName;

    @ApiModelProperty(value = "岗位性质")
    private PositionName positionName;

    @ApiModelProperty(value = "管理员权限")
    private Boolean superAdmin;


}
