package com.lgyun.system.user.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author tzq
 * @since 2020/6/6 00:57
 */
@Data
@ApiModel(description = "用户信息")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户基础信息
     */
    private User user;

    /**
     * 权限标识集合
     */
    private List<String> permissions;

    /**
     * 角色集合
     */
    private List<String> roles;

}
