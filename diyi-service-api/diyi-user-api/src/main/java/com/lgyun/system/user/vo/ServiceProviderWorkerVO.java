package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 视图实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:28
 */
@Data
public class ServiceProviderWorkerVO implements Serializable {
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
     * 菜单名称集合
     */
    private List<String> menuNames;

    /**
     * 账户状态描述
     */
    private String accountState;

    /**
     * 是否是主账号
     */
    private Boolean master = false;

}
