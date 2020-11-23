package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/service-provider/permission")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---权限管理模块相关接口", tags = "服务商端---权限管理模块相关接口")
public class PermissionServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;


    @PostMapping("/create-or-update-role-menus")
    @ApiOperation(value = "角色管理---创建或修改角色及菜单的分配", notes = "角色管理---创建或修改角色及菜单的分配")
    public R createOrUpdateRoleMenus(@Valid @RequestBody RoleMenusDTO roleMenusDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderWorkerService.createOrUpdateRoleMenus(roleMenusDTO, serviceProviderWorkerEntity);
    }

    @GetMapping("/query-role-list")
    @ApiOperation(value = "角色管理---查询当前商户人员的角色列表", notes = "角色管理---查询当前商户人员的角色列表")
    public R queryRoleList(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return serviceProviderWorkerService.queryRoleList(serviceProviderWorkerEntity.getId());
    }

    @GetMapping("/query-role-Info")
    @ApiOperation(value = "角色管理---查询角色详情", notes = "角色管理---查询角色详情")
    public R queryRoleInfo(@NotNull(message = "请选择角色") @RequestParam(required = false) Long roleId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return serviceProviderWorkerService.queryRoleInfo(roleId);
    }

    @GetMapping("/remove-role")
    @ApiOperation(value = "角色管理---删除角色", notes = "角色管理---删除角色")
    public R removeRole(@NotNull(message = "请选择要删除的角色") @RequestParam(required = false) Long roleId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return serviceProviderWorkerService.removeRole(roleId);
    }

    @GetMapping("/query-role")
    @ApiOperation(value = "子账号管理---查询当前商户人员创建的角色(分配角色时显示的下拉框)", notes = "子账号管理---查询当前商户人员创建的角色(分配角色时显示的下拉框)")
    public R queryRole(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return serviceProviderWorkerService.queryRole(serviceProviderWorkerEntity.getId());
    }

    @GetMapping("/query-child-account-list")
    @ApiOperation(value = "子账号管理---查询当前商户人员的所有主子账号", notes = "子账号管理---查询当前商户人员的所有主子账号")
    public R queryChildAccountList(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return serviceProviderWorkerService.queryChildAccountList(serviceProviderWorkerEntity.getId());
    }

    @GetMapping("/query-account-detail")
    @ApiOperation(value = "子账号管理---查询商户人员的账号详情", notes = "子账号管理---查询商户人员的账号详情")
    public R queryAccountDetail(@NotNull(message = "请选择子账号") @RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        return serviceProviderWorkerService.queryAccountDetail(serviceProviderWorkerEntity.getId(), accountId);
    }


    @PostMapping("/create-or-update-child-account")
    @ApiOperation(value = "子账号管理---创建或修改子账号及子账号的角色分配", notes = "子账号管理---创建或修改子账号及子账号的角色分配")
    public R createOrUpdateChildAccount(@Valid @RequestBody ChildAccountDTO childAccountDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();
        if (!serviceProviderWorkerEntity.getAdminPower()) {
            return R.fail("您没有权限创建子账号！");
        }
        return serviceProviderWorkerService.createOrUpdateChildAccount(childAccountDTO, serviceProviderWorkerEntity);
    }

    @PostMapping("/operate-child-account")
    @ApiOperation(value = "子账号管理---删除、停用、启用子账号", notes = "子账号管理---删除、停用、启用子账号")
    public R operateChildAccount(@NotNull(message = "请选择子账号") @RequestParam(required = false) Long childAccountId, ChildAccountType childAccountType, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderWorkerService.operateChildAccount(childAccountId, childAccountType, serviceProviderWorkerEntity.getId());
    }
}
