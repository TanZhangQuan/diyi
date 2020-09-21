package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.core.mp.base.TenantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User 实体类
 *
 * @author tzq
 * @since 2020/6/6 00:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class User extends TenantEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户类型
	 */
	private UserType userType;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 部门id
	 */
	private String deptId;

	/**
	 * 部门id
	 */
	private String postId;

}
