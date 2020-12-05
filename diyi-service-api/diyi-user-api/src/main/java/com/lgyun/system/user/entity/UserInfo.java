package com.lgyun.system.user.entity;

import com.lgyun.common.enumeration.UserType;
import lombok.Data;

import java.io.Serializable;

/**
 * 封装用户信息
 *
 * @author tzq
 * @since 2020/6/6 00:57
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机
     */
    private String phone;

}
