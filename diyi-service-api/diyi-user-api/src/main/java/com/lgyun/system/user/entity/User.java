package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.tool.DateUtil;
import com.lgyun.core.mp.base.TenantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

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
	 * 商户员工账户状态
	 */
	private AccountState userState = AccountState.NORMAL;

	/**
	 * 编号
	 */
	private String code;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String name;

	/**
	 * 真名
	 */
	private String realName;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 生日
	 */
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
	private Date birthday;

	/**
	 * 性别
	 */
	private Integer sex;

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
