package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.MaterialState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.*;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/service-provider")
@Validated
@AllArgsConstructor
@Api(value = "平台端---服务商管理模块相关接口", tags = "平台端---服务商管理模块相关接口")
public class ServiceProviderAdminController {

    private IAdminService adminService;
    private IServiceProviderService serviceProviderService;
    private IServiceProviderCertService serviceProviderCertService;
    private IAdminCenterMaterialService adminCenterMaterialService;
    private IEnterpriseServiceProviderService enterpriseProviderService;

    @PostMapping("/create-service-provider")
    @ApiOperation(value = "添加服务商", notes = "添加服务商")
    public R createServiceProvider(@Valid @RequestBody AddServiceProviderDTO addServiceProviderDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return serviceProviderService.createServiceProvider(addServiceProviderDTO, adminEntity);
    }

    @PostMapping("/update-service-provider")
    @ApiOperation(value = "编辑服务商", notes = "编辑服务商")
    public R updateServiceProvider(@Valid @RequestBody UpdateServiceProviderDTO updateServiceProviderDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return serviceProviderService.updateServiceProvider(updateServiceProviderDTO, adminEntity);
    }

    @GetMapping("/query-service-provider-update-detail")
    @ApiOperation(value = "查询编辑服务商详情", notes = "查询编辑服务商详情")
    public R queryServiceProviderUpdateDetail(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.queryServiceProviderUpdateDetail(serviceProviderId);
    }

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(QueryServiceProviderListDTO queryServiceProviderListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.queryServiceProviderListAdmin(queryServiceProviderListDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/update-service-provider-state")
    @ApiOperation(value = "更改服务商状态", notes = "更改服务商状态")
    public R updateServiceProviderState(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                        @ApiParam(value = "服务商状态") @NotNull(message = "请选择服务商状态") @RequestParam(required = false) AccountState serviceProviderState, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.updateServiceProviderState(serviceProviderId, serviceProviderState);
    }

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询所有商户编号姓名", notes = "查询所有商户编号姓名")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderService.queryEnterpriseIdAndNameList(null, enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/match-enterprise")
    @ApiOperation(value = "服务商匹配商户", notes = "服务商匹配商户")
    public R matchServiceProvider(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                  @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                  @ApiParam(value = "分配说明") @RequestParam(required = false) String matchDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return enterpriseProviderService.relevanceEnterpriseServiceProvider(enterpriseId, serviceProviderId, matchDesc, adminEntity);
    }

    @GetMapping("/query-cooperation-enterprise-list")
    @ApiOperation(value = "查询当前服务商合作商户", notes = "查询当前服务商合作商户")
    public R queryCooperationEnterpriseList(@ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                            @ApiParam(value = "服务商名称", required = true) @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderService.getEnterprtisesByServiceProviderId(serviceProviderId, serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-service-provider-cert-list")
    @ApiOperation(value = "查询服务商资格信息", notes = "查询服务商资格信息")
    public R queryServiceProviderCertList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderCertService.queryServiceProviderCertList(serviceProviderId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-or-update-service-provider-cert")
    @ApiOperation(value = "添加或修改服务商资格信息", notes = "添加或修改服务商资格信息")
    public R addOrUpdateServiceProviderCert(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                            @Valid @RequestBody AddOrUpdateServiceProviderCertDTO addOrUpdateServiceProviderCertDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderCertService.addOrUpdateServiceProviderCert(serviceProviderId, addOrUpdateServiceProviderCertDTO);
    }

    @GetMapping("/query-service-provider-cert-update-detail")
    @ApiOperation(value = "查询编辑服务商资格信息", notes = "查询编辑服务商资格信息")
    public R queryServiceProviderCertUpdateDetail(@ApiParam(value = "服务商资格") @NotNull(message = "请选择服务商资格") @RequestParam(required = false) Long serviceProviderCertId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderCertService.queryServiceProviderCertUpdateDetail(serviceProviderCertId);
    }

    @PostMapping("/remove-service-provider-cert-list")
    @ApiOperation(value = "删除服务商资格信息", notes = "删除服务商资格信息")
    public R removeServiceProviderCertList(@ApiParam(value = "服务商资格信息", required = true) @NotNull(message = "请选择要删除的服务商资格信息") @RequestParam(required = false) Long serviceProviderCertId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(serviceProviderCertService.removeById(serviceProviderCertId));
    }

    @GetMapping("/query-admin-center-material-list")
    @ApiOperation(value = "查询服务商综合业务资料(模板管理)", notes = "查询服务商综合业务资料(模板管理)")
    public R queryAdminCenterMaterialList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return adminCenterMaterialService.queryAdminCenterMaterialList(serviceProviderId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-or-update-admin-center-material")
    @ApiOperation(value = "创建或编辑服务商综合业务资料(模板管理)", notes = "创建或编辑服务商综合业务资料(模板管理)")
    public R addOrUpdateAdminCenterMaterial(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                            @Valid @RequestBody AddOrUpdateAdminCenterMaterialDTO addOrUpdateAdminCenterMaterialDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return adminCenterMaterialService.addOrUpdateAdminCenterMaterial(serviceProviderId, addOrUpdateAdminCenterMaterialDTO);
    }

    @GetMapping("/query-admin-center-material-update-detail")
    @ApiOperation(value = "查询编辑服务商综合业务资料(模板管理)详情", notes = "查询编辑服务商综合业务资料(模板管理)详情")
    public R queryAdminCenterMaterialUpdateDetail(@ApiParam(value = "综合业务资料") @NotNull(message = "请选择综合业务资料") @RequestParam(required = false) Long adminCenterMaterialId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return adminCenterMaterialService.queryAdminCenterMaterialUpdateDetail(adminCenterMaterialId);
    }

    @PostMapping("/remove-admin-center-material")
    @ApiOperation(value = "删除服务商综合业务资料(模板管理)", notes = "删除服务商综合业务资料(模板管理)")
    public R queryAdminCenterMaterialList(@ApiParam(value = "综合业务资料", required = true) @NotNull(message = "请选择要删除的综合业务资料") @RequestParam(required = false) Long adminCenterMaterialId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return R.status(adminCenterMaterialService.removeById(adminCenterMaterialId));
    }

    @PostMapping("/update-admin-center-material-state")
    @ApiOperation(value = "更改服务商综合业务资料(模板管理)状态", notes = "更改服务商综合业务资料(模板管理)状态")
    public R updateAdminCenterMaterialState(@ApiParam(value = "综合业务资料", required = true) @NotNull(message = "请选择综合业务资料") @RequestParam(required = false) Long adminCenterMaterialId,
                                            @ApiParam(value = "综合业务资料状态", required = true) @NotNull(message = "请选择综合业务资料状态") @RequestParam(required = false) MaterialState materialState, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return adminCenterMaterialService.updateAdminCenterMaterialState(adminCenterMaterialId, materialState);
    }

}
