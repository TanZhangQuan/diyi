package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

import java.io.Serializable;

@Data
public class AgentMainWorkerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商名称
     */
    private String agentMainName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String workerName;

    /**
     * 岗位性质
     */
    private PositionName positionName;

    /**
     * 管理员权限
     */
    private Boolean superAdmin;

}
