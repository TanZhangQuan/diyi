package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统角色 实体类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_role")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 账号
     */
    private Long account;

    /**
     * 角色名
     */
    private String roleName;

}
