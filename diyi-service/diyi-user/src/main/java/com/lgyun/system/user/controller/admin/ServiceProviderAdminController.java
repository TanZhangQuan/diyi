package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.MaterialState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.*;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 平台端---服务商管理controller
 *
 * @author tzq
 * @date 2020-09-9
 */
@Slf4j
@RestController
@RequestMapping("/admin/service-provider")
@Validated
@AllArgsConstructor
@Api(value = "平台端---服务商管理模块相关接口", tags = "平台端---服务商管理模块相关接口")
public class ServiceProviderAdminController {

    private IServiceProviderService serviceProviderService;
    private IAdminService adminService;
    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IServiceProviderCertService serviceProviderCertService;
    private IAdminCenterMaterialService adminCenterMaterialService;

    @PostMapping("/create-service-provider")
    @ApiOperation(value = "添加服务商", notes = "添加服务商")
    public R createEnterprise(@Valid @RequestBody AddServiceProviderDTO addServiceProviderDTO, BladeUser bladeUser) {

        log.info("添加服务商");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();

            return serviceProviderService.createServiceProvider(addServiceProviderDTO, adminEntity);
        } catch (Exception e) {
            log.error("添加服务商异常", e);
        }

        return R.fail("操作失败");
    }

    @PostMapping("/update-service-provider")
    @ApiOperation(value = "修改服务商", notes = "修改服务商")
    public R updateServiceProvider(@Valid @RequestBody UpdateServiceProviderDTO updateServiceProviderDTO, BladeUser bladeUser) {

        log.info("修改服务商");
        try {
            //查询当前管理员
            R<AdminEntity> result = adminService.currentAdmin(bladeUser);
            if (!(result.isSuccess())){
                return result;
            }
            AdminEntity adminEntity = result.getData();

            return serviceProviderService.updateServiceProvider(updateServiceProviderDTO, adminEntity);
        } catch (Exception e) {
            log.error("修改服务商异常", e);
        }

        return R.fail("操作失败");
    }

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(QueryServiceProviderListDTO queryServiceProviderListDTO, Query query) {

        log.info("查询所有服务商");
        try {
            return serviceProviderService.queryServiceProviderListAdmin(queryServiceProviderListDTO, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询所有服务商异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-service-provider-detail")
    @ApiOperation(value = "查询服务商基本信息", notes = "查询服务商基本信息")
    public R queryServiceProviderDetail(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId) {

        log.info("查询服务商基本信息");
        try {
            return serviceProviderService.queryServiceProviderDetailServiceProvider(serviceProviderId);
        } catch (Exception e) {
            log.error("查询服务商基本信息异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/query-service-provider-cert-list")
    @ApiOperation(value = "查询服务商资格信息", notes = "查询服务商资格信息")
    public R queryServiceProviderCertList(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId, Query query) {

        log.info("查询服务商资格信息");
        try {
            return serviceProviderCertService.queryServiceProviderCertList(serviceProviderId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询服务商资格信息异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/add-or-update-service-provider-cert")
    @ApiOperation(value = "添加或修改服务商资格信息", notes = "添加或修改服务商资格信息")
    public R addOrUpdateServiceProviderCert(@Valid @RequestBody AddOrUpdateServiceProviderCertDTO addOrUpdateServiceProviderCertDTO) {
        log.info("添加或修改服务商资格信息");
        try {
            return serviceProviderCertService.addOrUpdateServiceProviderCert(addOrUpdateServiceProviderCertDTO);
        } catch (Exception e) {
            log.error("添加或修改服务商资格信息异常", e);
        }

        return R.fail("操作失败");
    }

    @PostMapping("/add-admin-center-material")
    @ApiOperation(value = "创建服务商综合业务资料(模板管理)", notes = "创建服务商综合业务资料(模板管理)")
    public R addAdminCenterMaterial(@Valid @RequestBody AddAdminCenterMaterialDTO addAdminCenterMaterialDTO) {

        log.info("创建服务商综合业务资料(模板管理)");
        try {
            return adminCenterMaterialService.addAdminCenterMaterial(addAdminCenterMaterialDTO);
        } catch (Exception e) {
            log.error("创建服务商综合业务资料(模板管理)异常", e);
        }
        return R.fail("创建失败");
    }

    @PostMapping("/update-admin-center-material")
    @ApiOperation(value = "编辑服务商综合业务资料(模板管理)", notes = "编辑服务商综合业务资料(模板管理)")
    public R updateAdminCenterMaterial(@Valid @RequestBody UpdateAdminCenterMaterialDTO updateAdminCenterMaterialDTO) {

        log.info("编辑服务商综合业务资料(模板管理)");
        try {
            return adminCenterMaterialService.updateAdminCenterMaterial(updateAdminCenterMaterialDTO);
        } catch (Exception e) {
            log.error("编辑服务商综合业务资料(模板管理)异常", e);
        }
        return R.fail("编辑失败");
    }

    @GetMapping("/query-admin-center-material-list")
    @ApiOperation(value = "查询服务商综合业务资料(模板管理)", notes = "查询服务商综合业务资料(模板管理)")
    public R queryAdminCenterMaterialList(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId, Query query) {

        log.info("查询服务商综合业务资料(模板管理)");
        try {
            return adminCenterMaterialService.queryAdminCenterMaterialList(serviceProviderId, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("查询服务商综合业务资料(模板管理)异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/remove-admin-center-material")
    @ApiOperation(value = "删除服务商综合业务资料(模板管理)", notes = "删除服务商综合业务资料(模板管理)")
    public R queryAdminCenterMaterialList(@ApiParam(value = "综合业务资料ID集合", required = true) @NotBlank(message = "请选择要删除的综合业务资料") @RequestParam(required = false) String ids) {

        log.info("删除服务商综合业务资料(模板管理)");
        try {
            return R.status(adminCenterMaterialService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("删除服务商综合业务资料(模板管理)异常", e);
        }
        return R.fail("删除失败");
    }

    @PostMapping("/update-admin-center-material-state")
    @ApiOperation(value = "更改服务商综合业务资料(模板管理)状态", notes = "更改服务商综合业务资料(模板管理)状态")
    public R updateAdminCenterMaterialState(@ApiParam(value = "综合业务资料ID", required = true) @NotBlank(message = "请输入综合业务资料编号") @RequestParam(required = false) Long adminCenterMaterialId,
                                            @ApiParam(value = "综合业务资料状态", required = true) @NotBlank(message = "请选择综合业务资料状态") @RequestParam(required = false) MaterialState materialState) {

        log.info("更改服务商综合业务资料(模板管理)状态");
        try {
            return adminCenterMaterialService.updateAdminCenterMaterialState(adminCenterMaterialId, materialState);
        } catch (Exception e) {
            log.error("更改服务商综合业务资料(模板管理)状态异常", e);
        }
        return R.fail("操作失败");
    }

    @PostMapping("/remove-service-provider-cert")
    @ApiOperation(value = "删除服务商资格信息", notes = "删除服务商资格信息")
    public R removeServiceProviderCert(@ApiParam(value = "服务商资格信息ID集合", required = true) @NotBlank(message = "请选择要删除的服务商资格信息") @RequestParam(required = false) String ids) {

        log.info("删除服务商资格信息");
        try {
            return R.status(serviceProviderCertService.removeByIds(Func.toLongList(ids)));
        } catch (Exception e) {
            log.error("删除服务商资格信息异常", e);
        }

        return R.fail("删除失败");
    }

    @GetMapping("/query-service-provider-worker-list")
    @ApiOperation(value = "查询服务商员工", notes = "查询服务商员工")
    public R queryServiceProviderWorkerList(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId,
                                            @ApiParam(value = "岗位性质") @NotNull(message = "请选择岗位性质") @RequestParam(required = false) PositionName positionName) {

        log.info("查询服务商员工");
        try {
            return serviceProviderWorkerService.queryServiceProviderWorkerList(serviceProviderId, positionName);
        } catch (Exception e) {
            log.error("查询服务商员工异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/update-service-provider-state")
    @ApiOperation(value = "更改服务商状态", notes = "更改服务商状态")
    public R updateServiceProviderState(@ApiParam(value = "服务商ID") @NotNull(message = "请输入服务商编号") @RequestParam(required = false) Long serviceProviderId,
                                        @ApiParam(value = "服务商状态") @NotNull(message = "请选择服务商状态") @RequestParam(required = false) AccountState serviceProviderState) {

        log.info("更改服务商状态");
        try {
            return serviceProviderService.updateServiceProviderState(serviceProviderId, serviceProviderState);
        } catch (Exception e) {
            log.error("更改服务商状态异常", e);
        }

        return R.fail("更改服务商状态失败");
    }

}
