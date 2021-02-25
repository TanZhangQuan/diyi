package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "商户员工详情VO")
public class EnterpriseWorkerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String workerName;

    @ApiModelProperty(value = "岗位性质")
    private PositionName positionName;

    @ApiModelProperty(value = "权限管理")
    private Boolean superAdmin;
}
