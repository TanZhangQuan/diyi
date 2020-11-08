package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class EnterpriseWorkerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @ApiModelProperty("商户员工ID（编辑时用）")
    private Long enterpriseWorkerId;

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String enterpriseName;

    /**
     * 岗位
     */
    @ApiModelProperty("岗位")
    private String positionName;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phoneNumber;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String employeeUserName;

    /**
     * 拥有的角色ID
     */
    @ApiModelProperty("拥有的角色ID(用来显示下拉框默认值)")
    private Long roleId;


}
