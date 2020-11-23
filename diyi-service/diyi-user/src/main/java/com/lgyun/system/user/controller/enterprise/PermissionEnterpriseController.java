package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/enterprise/permission")
@Validated
@AllArgsConstructor
@Api(value = "商户端---权限管理模块相关接口", tags = "商户端---权限管理模块相关接口")
public class PermissionEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;


    @PostMapping("/create-or-update-role-menus")
    @ApiOperation(value = "角色管理---创建或修改角色及菜单的分配", notes = "角色管理---创建或修改角色及菜单的分配")
    public R createOrUpdateRoleMenus(@Valid @RequestBody RoleMenusDTO roleMenusDTO, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseWorkerService.createOrUpdateRoleMenus(roleMenusDTO, enterpriseWorkerEntity);
    }

    @GetMapping("/query-role-list")
    @ApiOperation(value = "角色管理---查询当前商户人员的角色列表", notes = "角色管理---查询当前商户人员的角色列表")
    public R queryRoleList(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return enterpriseWorkerService.queryRoleList(enterpriseWorkerEntity.getId());
    }

    @GetMapping("/remove-role")
    @ApiOperation(value = "角色管理---删除角色", notes = "角色管理---删除角色")
    public R removeRole(@NotNull(message = "请选择要删除的角色") @RequestParam(required = false) Long roleId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return enterpriseWorkerService.removeRole(roleId);
    }

    @GetMapping("/query-role-Info")
    @ApiOperation(value = "角色管理---查询角色详情", notes = "角色管理---查询角色详情")
    public R queryRoleInfo(@NotNull(message = "请选择角色") @RequestParam(required = false) Long roleId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return enterpriseWorkerService.queryRoleInfo(roleId);
    }

    @GetMapping("/query-role")
    @ApiOperation(value = "子账号管理---查询当前商户人员创建的角色(分配角色时显示的下拉框)", notes = "子账号管理---查询当前商户人员创建的角色(分配角色时显示的下拉框)")
    public R queryRole(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return enterpriseWorkerService.queryRole(enterpriseWorkerEntity.getId());
    }

    @GetMapping("/query-child-account-list")
    @ApiOperation(value = "子账号管理---查询当前商户人员的所有主子账号", notes = "子账号管理---查询当前商户人员的所有主子账号")
    public R queryChildAccountList(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return enterpriseWorkerService.queryChildAccountList(enterpriseWorkerEntity.getId());
    }

    @GetMapping("/query-account-detail")
    @ApiOperation(value = "子账号管理---查询商户人员的账号详情", notes = "子账号管理---查询商户人员的账号详情")
    public R queryAccountDetail(@NotNull(message = "请选择子账号") @RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        return enterpriseWorkerService.queryAccountDetail(enterpriseWorkerEntity.getId(), accountId);
    }


    @PostMapping("/create-or-update-child-account")
    @ApiOperation(value = "子账号管理---创建或修改子账号及子账号的角色分配", notes = "子账号管理---创建或修改子账号及子账号的角色分配")
    public R createOrUpdateChildAccount(@Valid @RequestBody ChildAccountDTO childAccountDTO, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
        if (!enterpriseWorkerEntity.getAdminPower()) {
            return R.fail("您没有权限创建子账号！");
        }
        return enterpriseWorkerService.createOrUpdateChildAccount(childAccountDTO, enterpriseWorkerEntity);
    }

    @PostMapping("/operate-child-account")
    @ApiOperation(value = "子账号管理---删除、停用、启用子账号", notes = "子账号管理---删除、停用、启用子账号")
    public R operateChildAccount(@NotNull(message = "请选择子账号") @RequestParam(required = false) Long childAccountId, ChildAccountType childAccountType, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseWorkerService.operateChildAccount(childAccountId, childAccountType, enterpriseWorkerEntity.getId());
    }


}
