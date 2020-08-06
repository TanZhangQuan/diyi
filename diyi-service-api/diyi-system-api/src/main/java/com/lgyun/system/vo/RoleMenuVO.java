package com.lgyun.system.vo;

import com.lgyun.system.entity.RoleMenu;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单 视图实体类
 *
 * @author liangfeihu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RoleMenuVO对象", description = "RoleMenuVO对象")
public class RoleMenuVO extends RoleMenu {
	private static final long serialVersionUID = 1L;

}
