package com.lgyun.system.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * EnterpriseContactVO
 *
 * @author liangfeihu
 * @since 2020/8/19 11:40
 */
@Data
public class EnterpriseContactVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户员工账户状态
     */
    private String enterpriseWorkerState;

    /**
     * 姓名
     */
    private String workerName;

    /**
     * 性别
     */
    private String workerSex;

    /**
     * 岗位性质
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
     * 创建时间
     */
    private Long createTime;

    /**
     * 记录状态[0-非正常 1-正常]
     */
    private Integer status;

    /**
     * 是否已删除[0-未删除 1-已删除]
     */
    private Integer isDeleted;

}
