package com.lgyun.system.wrapper;

import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.node.INode;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.service.IRoleService;
import com.lgyun.system.vo.RoleVO;
import com.lgyun.system.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

    private static IRoleService roleService;

    static {
        roleService = SpringUtil.getBean(IRoleService.class);
    }

    public static RoleWrapper build() {
        return new RoleWrapper();
    }

    @Override
    public RoleVO entityVO(Role role) {
        RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
        if (Func.equals(role.getParentId(), CommonConstant.TOP_PARENT_ID)) {
            roleVO.setParentName(CommonConstant.TOP_PARENT_NAME);
        } else {
            Role parent = roleService.getById(role.getParentId());
            roleVO.setParentName(parent.getRoleName());
        }
        return roleVO;
    }

    public List<INode> listNodeVO(List<Role> list) {
        List<INode> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }

}
