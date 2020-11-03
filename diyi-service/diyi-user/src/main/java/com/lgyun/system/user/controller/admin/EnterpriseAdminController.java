package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateEnterpriseDTO;
import com.lgyun.system.user.dto.QueryEnterpriseListDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "平台端---商户管理模块相关接口", tags = "平台端---商户管理模块相关接口")
public class EnterpriseAdminController {

    private IAdminService adminService;
    private IEnterpriseService enterpriseService;
    private IEnterpriseServiceProviderService enterpriseProviderService;

    @PostMapping("/create-or-update-enterprise")
    @ApiOperation(value = "添加或编辑商户", notes = "添加或编辑商户")
    public R createOrUpdateEnterprise(@Valid @RequestBody AddOrUpdateEnterpriseDTO addOrUpdateEnterpriseDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return enterpriseService.createOrUpdateEnterprise(addOrUpdateEnterpriseDTO, adminEntity);
    }

    @GetMapping("/query-enterprise-update-detail")
    @ApiOperation(value = "查询编辑商户详情", notes = "查询编辑商户详情")
    public R queryEnterpriseUpdateDetail(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseUpdateDetail(enterpriseId);
    }

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询所有商户", notes = "查询所有商户")
    public R queryEnterpriseList(QueryEnterpriseListDTO queryEnterpriseListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseListEnterprise(queryEnterpriseListDTO, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/update-enterprise-state")
    @ApiOperation(value = "更改商户状态", notes = "更改商户状态")
    public R updateEnterpriseState(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                   @ApiParam(value = "商户状态", required = true) @NotNull(message = "请选择商户状态") @RequestParam(required = false) AccountState enterpriseState, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.updateEnterpriseState(enterpriseId, enterpriseState);
    }

    @GetMapping("/query-service-provider-id-and-name-list")
    @ApiOperation(value = "查询所有服务商编号姓名", notes = "查询所有服务商编号姓名")
    public R queryServiceProviderIdAndNameList(@ApiParam(value = "服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderService.queryServiceProviderIdAndNameList(null, serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/match-service-provider")
    @ApiOperation(value = "商户匹配服务商", notes = "商户匹配服务商")
    public R matchServiceProvider(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                  @ApiParam(value = "服务商", required = true) @NotEmpty(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                  @ApiParam(value = "分配说明") @RequestParam(required = false) String matchDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return enterpriseProviderService.relevanceEnterpriseServiceProvider(enterpriseId, serviceProviderId, matchDesc, adminEntity);
    }

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询商户合作服务商", notes = "查询商户合作服务商")
    public R queryCooperationServiceProviderList(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryCooperationServiceProviderList(enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/update-cooperation-status")
    @ApiOperation(value = "更改商户服务商合作关系", notes = "更改商户服务商合作关系")
    public R updateCooperationStatus(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                     @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                     @ApiParam(value = "合作状态", required = true) @NotNull(message = "请选择合作状态") @RequestParam(required = false) CooperateStatus cooperateStatus, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderService.updateCooperationStatus(enterpriseId, serviceProviderId, cooperateStatus);
    }

}
