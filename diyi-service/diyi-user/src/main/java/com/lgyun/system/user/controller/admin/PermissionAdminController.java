package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/permission")
@Validated
@AllArgsConstructor
@Api(value = "平台端---权限管理模块相关接口", tags = "平台端---权限管理模块相关接口")
public class PermissionAdminController {

    private IAdminService adminService;

    @PostMapping("/create-or-update-role-menus")
    @ApiOperation(value = "角色管理---创建或修改角色及菜单的分配", notes = "角色管理---创建或修改角色及菜单的分配")
    public R createOrUpdateRoleMenus(@Valid @RequestBody RoleMenusDTO roleMenusDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return adminService.createOrUpdateRoleMenus(roleMenusDTO, adminEntity);
    }

    @GetMapping("/query-role-list")
    @ApiOperation(value = "角色管理---查询当前管理人员的角色列表", notes = "角色管理---查询当前管理人员的角色列表")
    public R queryRoleList(BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return adminService.queryRoleList(adminEntity.getId());
    }

    @GetMapping("/query-role-Info")
    @ApiOperation(value = "角色管理---查询角色详情", notes = "角色管理---查询角色详情")
    public R queryRoleInfo(@NotNull(message = "请选择角色") @RequestParam(required = false) Long roleId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return adminService.queryRoleInfo(roleId);
    }

    @GetMapping("/remove-role")
    @ApiOperation(value = "角色管理---删除角色", notes = "角色管理---删除角色")
    public R removeRole(@NotNull(message = "请选择要删除的角色") @RequestParam(required = false) Long roleId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return adminService.removeRole(roleId);
    }

    @GetMapping("/query-child-account-list")
    @ApiOperation(value = "子账号管理---查询当前管理员的所有主子账号", notes = "子账号管理---查询当前管理员的所有主子账号")
    public R queryChildAccountList(BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return adminService.queryChildAccountList(adminEntity.getId());
    }

    @GetMapping("/query-account-detail")
    @ApiOperation(value = "子账号管理---查询管理员的账号详情", notes = "子账号管理---查询商户账号详情")
    public R queryAccountDetail(@NotNull(message = "请选择子账号") @RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return adminService.queryAccountDetail(adminEntity.getId(), accountId);
    }

    @GetMapping("/query-role")
    @ApiOperation(value = "子账号管理---查询当前管理员创建的角色(分配角色时显示的下拉框)", notes = "子账号管理---查询当前管理员创建的角色(分配角色时显示的下拉框)")
    public R queryRole(BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return adminService.queryRole(adminEntity.getId());
    }

    @PostMapping("/create-or-update-child-account")
    @ApiOperation(value = "子账号管理---创建或修改子账号及子账号的角色分配", notes = "子账号管理---创建或修改子账号及子账号的角色分配")
    public R createOrUpdateChildAccount(@Valid @RequestBody ChildAccountDTO childAccountDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        if (!(adminEntity.getAdminPower())) {
            return R.fail("您没有权限创建子账号！");
        }
        return adminService.createOrUpdateChildAccount(childAccountDTO, adminEntity.getId());
    }

    @PostMapping("/operate-child-account")
    @ApiOperation(value = "子账号管理---删除、停用、启用子账号", notes = "子账号管理---删除、停用、启用子账号")
    public R operateChildAccount(@NotNull(message = "请选择子账号") @RequestParam(required = false) Long childAccountId, ChildAccountType childAccountType, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return adminService.operateChildAccount(childAccountId, childAccountType, adminEntity.getId());
    }

}

