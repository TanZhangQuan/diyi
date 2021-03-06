package com.lgyun.system.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.controller.BladeController;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.node.TreeNode;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.service.IMenuService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Menu 控制器
 *
 * @author Chill
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "菜单", tags = "菜单")
public class MenuController extends BladeController {

    private IUserClient userClient;
    private IMenuService menuService;

    @PostMapping("/submit")
    @ApiOperation(value = "新增或修改", notes = "传入menu")
    public R submit(@Valid @RequestBody Menu menu) {
        return R.status(menuService.saveOrUpdate(menu));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(menuService.removeByIds(Func.toLongList(ids)));
    }

    @GetMapping("/routes")
    @ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
    public R<List<MenuVO>> routes(BladeUser user) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        List<MenuVO> list = menuService.routes(enterpriseWorkerEntity.getRoleId().toString(), UserType.ENTERPRISE,enterpriseWorkerEntity.getSuperAdmin());
        return R.data(list);
    }

    @GetMapping("/routes/service")
    @ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
    public R<List<MenuVO>> routesService(BladeUser user) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        List<MenuVO> list = menuService.routes(String.valueOf(serviceProviderWorkerEntity.getRoleId()), UserType.SERVICEPROVIDER, serviceProviderWorkerEntity.getSuperAdmin());
        return R.data(list);
    }

    @GetMapping("/routes/admin")
    @ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
    public R<List<MenuVO>> routesAdmin(BladeUser user) {
        //查询当前创客
        R<AdminEntity> result = userClient.currentAdmin(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        AdminEntity adminEntity = result.getData();

        List<MenuVO> list = menuService.routes(String.valueOf(adminEntity.getRoleId()), UserType.ADMIN, adminEntity.getSuperAdmin());
        return R.data(list);
    }

    @GetMapping("/routes/agent-main")
    @ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
    public R<List<MenuVO>> routesAgentMain(BladeUser user) {
        //查询当前创客
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        List<MenuVO> list = menuService.routes(String.valueOf(agentMainWorkerEntity.getRoleId()), UserType.AGENTMAIN, agentMainWorkerEntity.getSuperAdmin());
        return R.data(list);
    }

    @GetMapping("/tree")
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<TreeNode>> tree(BladeUser user) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        List<TreeNode> tree = menuService.tree(MenuType.ENTERPRISE, enterpriseWorkerEntity.getRoleId(), enterpriseWorkerEntity.getSuperAdmin());
        return R.data(tree);
    }

    @GetMapping("/tree/service")
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<TreeNode>> treeService(BladeUser user) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        List<TreeNode> tree = menuService.tree(MenuType.SERVICEPROVIDER, serviceProviderWorkerEntity.getRoleId(), serviceProviderWorkerEntity.getSuperAdmin());
        return R.data(tree);
    }

    @GetMapping("/tree/admin")
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<TreeNode>> treeAdmin(BladeUser user) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        AdminEntity adminEntity = result.getData();

        List<TreeNode> tree = menuService.tree(MenuType.ADMIN, adminEntity.getRoleId(), adminEntity.getSuperAdmin());
        return R.data(tree);
    }

    @GetMapping("/tree/agent-main")
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<TreeNode>> treeAgentMain(BladeUser user) {
        //查询当前创客
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(user);
        if (!(result.isSuccess())) {
            return R.fail("当前登录用户失效");
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        List<TreeNode> tree = menuService.tree(MenuType.AGENTMAIN, agentMainWorkerEntity.getRoleId(), agentMainWorkerEntity.getSuperAdmin());
        return R.data(tree);
    }

    @GetMapping("/role-tree-keys")
    @ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
    public R<List<String>> roleTreeKeys(String roleIds) {
        return R.data(menuService.roleTreeKeys(roleIds));
    }

}
