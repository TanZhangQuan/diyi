package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.system.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends User {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 岗位名
     */
    private String postName;

}
