package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AgentMainWorkerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道商名称")
    private String agentMainName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓名")
    private String workerName;

    @ApiModelProperty(value = "岗位性质")
    private PositionName positionName;

    @ApiModelProperty(value = "管理员权限")
    private Boolean superAdmin;

}
