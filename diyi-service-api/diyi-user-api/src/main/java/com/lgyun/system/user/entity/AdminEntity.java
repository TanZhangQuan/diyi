package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sun.rmi.runtime.Log;

/**
 * 平台管理员信息表 Entity
 *
 * @author tzq
 * @since 2020-09-19 15:02:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_admin")
public class AdminEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 管理者ID
     */
    private Long userId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 管理员账户状态
     */
    private AccountState adminState;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String workerSex;

    /**
     * 岗位性质
     */
    private PositionName positionName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 上级主管
     */
    private Long upLevelId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String loginPwd;

    /**
     * 拥有的角色ID
     */
    private Long roleId;

    /**
     * 管理员权限特性(为true时拥有全部权限)
     */
    private Boolean superAdmin = false;

    /**
     * 管理员特性（为true时可以创建子账号）
     */
    private Boolean adminPower = false;
}
