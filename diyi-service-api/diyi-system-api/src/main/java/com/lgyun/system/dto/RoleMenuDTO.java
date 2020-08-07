package com.lgyun.system.dto;

import com.lgyun.system.entity.RoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单 数据传输对象实体类
 *
 * @author liangfeihu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuDTO extends RoleMenu {
    private static final long serialVersionUID = 1L;

}
