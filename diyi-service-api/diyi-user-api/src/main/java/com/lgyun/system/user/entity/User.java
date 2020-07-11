package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.core.mp.base.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * User 实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 00:58
 */
@Data
@TableName("blade_user")
@EqualsAndHashCode(callSuper = true)
public class User extends TenantEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 用户类型
	 */
	private UserType userType;

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
