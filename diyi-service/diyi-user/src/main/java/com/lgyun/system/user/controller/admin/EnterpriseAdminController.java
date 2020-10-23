package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddEnterpriseDTO;
import com.lgyun.system.user.dto.admin.QueryEnterpriseListDTO;
import com.lgyun.system.user.dto.admin.UpdateEnterpriseDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
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
    private IEnterpriseWorkerService enterpriseWorkerService;

    @PostMapping("/create-enterprise")
    @ApiOperation(value = "添加商户", notes = "添加商户")
    public R createEnterprise(@Valid @RequestBody AddEnterpriseDTO addEnterpriseDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return enterpriseService.createEnterprise(addEnterpriseDTO, adminEntity);
    }

    @PostMapping("/update-enterprise")
    @ApiOperation(value = "修改商户", notes = "修改商户")
    public R updateEnterprise(@Valid @RequestBody UpdateEnterpriseDTO updateEnterpriseDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return enterpriseService.updateEnterprise(updateEnterpriseDTO, adminEntity);
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

    @GetMapping("/query-enterprise-detail")
    @ApiOperation(value = "查询商户基本信息", notes = "查询商户基本信息")
    public R queryEnterpriseDetail(@ApiParam(value = "商户ID", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseDetailEnterprise(enterpriseId);
    }

    @GetMapping("/query-enterprise-worker-list")
    @ApiOperation(value = "查询商户员工", notes = "查询商户员工")
    public R queryEnterpriseWorkerList(@ApiParam(value = "商户ID", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                                       @ApiParam(value = "岗位性质", required = true) @NotNull(message = "请选择岗位性质") @RequestParam(required = false) PositionName positionName, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseWorkerService.queryEnterpriseWorkerList(enterpriseId, positionName);
    }

    @PostMapping("/update-enterprise-state")
    @ApiOperation(value = "更改商户状态", notes = "更改商户状态")
    public R updateEnterpriseState(@ApiParam(value = "商户ID", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                                   @ApiParam(value = "商户状态", required = true) @NotNull(message = "请选择商户状态") @RequestParam(required = false) AccountState enterpriseState, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.updateEnterpriseState(enterpriseId, enterpriseState);
    }

    @GetMapping("/query-service-provider-id-name-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderIdNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), null, serviceProviderName);
    }

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询商户编号名称", notes = "查询商户编号名称")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户ID", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseIdAndName(enterpriseId);
    }

    @PostMapping("/relevance-enterprise-service-provider")
    @ApiOperation(value = "商户匹配服务商", notes = "商户匹配服务商")
    public R relevanceEnterpriseServiceProvider(@ApiParam(value = "商户ID", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId,
                                                @ApiParam(value = "服务商ID集合", required = true) @NotEmpty(message = "请选择服务商") @RequestParam(required = false) List<Long> serviceProviderIdList, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return enterpriseProviderService.relevanceEnterpriseServiceProvider(enterpriseId, serviceProviderIdList, adminEntity);
    }

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询商户合作服务商", notes = "查询商户合作服务商")
    public R queryCooperationServiceProviderList(@ApiParam(value = "商户ID", required = true) @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryCooperationServiceProviderList(enterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

}
