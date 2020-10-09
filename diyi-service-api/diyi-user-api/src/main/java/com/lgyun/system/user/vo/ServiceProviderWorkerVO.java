package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 视图实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceProviderWorkerVO extends ServiceProviderWorkerEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 账户状态value
     */
    private String accountStateValue;

    /**
     * 账户状态描述
     */
    private String accountStateDesc;

    /**
     * 员工性别value
     */
    private String workerSexValue;

    /**
     * 员工性别描述
     */
    private String workerSexDesc;

    /**
     * 岗位value
     */
    private String positionNameValue;

    /**
     * 岗位描述
     */
    private String positionNameDesc;

    /**
     * 菜单集合,勾选菜单title集合
     */
    private List<String> menuNameList;

    /**
     * 菜单集合,勾选菜单Id集合
     */
    private List<String> menuIds;
}
