package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
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
@RequestMapping("/admin/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "平台端---个体户管理模块相关接口", tags = "平台端---个体户管理模块相关接口")
public class IndividualBusinessAdminController {

    private IAdminService adminService;
    private IIndividualBusinessService individualBusinessService;
    private IEnterpriseReportService enterpriseReportService;
    private IMakerService makerService;
    private IEnterpriseServiceProviderService enterpriseServiceProviderService;

    @GetMapping("/query-maker-list")
    @ApiOperation(value = "查询创客", notes = "查询创客")
    public R queryMakerList(MakerListIndividualDTO makerListIndividualDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return makerService.queryMakerListIndividual(null, makerListIndividualDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/add-or-update-individual-business")
    @ApiOperation(value = "添加/编辑个体户", notes = "添加/编辑个体户")
    public R addOrUpdateIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.addOrUpdateIndividualBusiness(individualBusinessEnterpriseAddOrUpdateDto, null);
    }

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询所有个体户", notes = "查询所有个体户")
    public R queryIndividualBusinessList(IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryIndividualBusinessList(null, null, individualBusinessEnterpriseListDto, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R queryIndividualBusinessDetail(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryIndividualBusinessDetail(individualBusinessId);
    }

    @GetMapping("/query-update-individual-business-detail")
    @ApiOperation(value = "查询编辑个体户详情", notes = "查询编辑个体户详情")
    public R queryUpdateIndividualBusinessDetail(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryUpdateIndividualBusinessDetail(individualBusinessId);
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个体户年审信息", notes = "查询个体户年审信息")
    public R queryEnterpriseReportList(@ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findByBodyTypeAndBodyId(BodyType.INDIVIDUALBUSINESS, individualBusinessId, query);
    }

    @GetMapping("/query-service-provider-id-and-name-list")
    @ApiOperation(value = "查询服务商", notes = "查询服务商")
    public R queryServiceProviderIdAndNameList(@ApiParam(value = "服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseServiceProviderService.queryServiceProviderIdAndNameList(null, serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/mate-service-provider")
    @ApiOperation(value = "匹配服务商", notes = "匹配服务商")
    public R mateServiceProvider(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                 @ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.mateServiceProvider(serviceProviderId, individualBusinessId);
    }

}
