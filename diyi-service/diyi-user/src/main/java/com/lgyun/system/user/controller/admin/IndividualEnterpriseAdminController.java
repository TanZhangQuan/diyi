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
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "平台端---个独管理模块相关接口", tags = "平台端---个独管理模块相关接口")
public class IndividualEnterpriseAdminController {

    private IAdminService adminService;
    private IMakerService makerService;
    private IIndividualEnterpriseService individualEnterpriseService;
    private IEnterpriseReportService enterpriseReportService;
    private IServiceProviderService serviceProviderService;

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

    @PostMapping("/add-or-update-individual-enterprise")
    @ApiOperation(value = "添加/编辑个独", notes = "添加/编辑个独")
    public R addOrUpdateIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.addOrUpdateIndividualEnterprise(individualBusinessEnterpriseAddOrUpdateDto, null);
    }

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询所有个独", notes = "查询所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个独编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个独名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryIndividualEnterpriseList(IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryIndividualEnterpriseList(Condition.getPage(query.setDescs("t1.create_time")), null, null, individualBusinessEnterpriseListDto);
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个独详情", notes = "查询个独详情")
    public R queryIndividualBusinessDetail(@ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryIndividualEnterpriseDetail(individualEnterpriseId);
    }

    @GetMapping("/query-update-individual-business-detail")
    @ApiOperation(value = "查询编辑个独详情", notes = "查询编辑个独详情")
    public R queryUpdateIndividualBusinessDetail(@ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.queryUpdateIndividualEnterpriseDetail(individualEnterpriseId);
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReportList(Query query, @ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findByBodyTypeAndBodyId(BodyType.INDIVIDUALENTERPRISE, individualEnterpriseId, query);
    }

    @GetMapping("/query-service-provider-id-and-name-list")
    @ApiOperation(value = "查询服务商", notes = "查询服务商")
    public R queryServiceProviderIdAndNameList(@ApiParam(value = "服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.queryServiceProviderIdAndNameList(null, serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/mate-service-provider")
    @ApiOperation(value = "匹配服务商", notes = "匹配服务商")
    public R mateServiceProvider(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                 @ApiParam(value = "个独") @NotNull(message = "请选择个独") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.mateServiceProvider(serviceProviderId, individualEnterpriseId);
    }

}
