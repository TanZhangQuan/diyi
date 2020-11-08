package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商户端---首页管理---商户员工详情VO
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class EnterpriseWorkerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String enterpriseName;

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
     * 权限管理
     */
    private Boolean superAdmin;
}
