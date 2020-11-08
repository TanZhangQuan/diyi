package com.lgyun.system.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 视图实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
public class ServiceProviderWorkerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 岗位
     */
    private String positionName;


    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户名
     */
    private String employeeUserName;

    /**
     * 角色ID
     */
    private Long roleId;

}
