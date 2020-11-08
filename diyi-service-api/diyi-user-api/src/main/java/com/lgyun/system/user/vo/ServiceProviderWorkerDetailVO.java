package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务商端---首页管理---服务商员工详情VO
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class ServiceProviderWorkerDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

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
