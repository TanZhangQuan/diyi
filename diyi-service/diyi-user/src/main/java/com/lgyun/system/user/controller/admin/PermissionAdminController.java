package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IAgentMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/permission")
@Validated
@AllArgsConstructor
@Api(value = "平台端---权限管理模块相关接口", tags = "平台端---权限管理模块相关接口")
public class PermissionAdminController {

    private IAdminService adminService;

    @GetMapping("/query-child-account-list")
    @ApiOperation(value = "查询当前管理员的所有主子账号", notes = "查询商户所有主子账号")
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
    @ApiOperation(value = "查询商户账号详情", notes = "查询商户账号详情")
    public R queryAccountDetail(@RequestParam("accountId") Long accountId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return adminService.queryAccountDetail(accountId);
    }
}

