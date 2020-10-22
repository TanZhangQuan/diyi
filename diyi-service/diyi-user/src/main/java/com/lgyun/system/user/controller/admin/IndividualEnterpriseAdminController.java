package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
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
    private IIndividualEnterpriseService individualEnterpriseService;
    private IEnterpriseReportService enterpriseReportService;

    @GetMapping("/query-individual-enterprise-list")
    @ApiOperation(value = "查询所有个独", notes = "查询所有个独")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个独编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个独名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryIndividualEnterpriseList(IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.getIndividualEnterpriseList(Condition.getPage(query.setDescs("create_time")), null, null, individualBusinessEnterpriseDto);
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReportList(Query query, @ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findByBodyTypeAndBodyId(query, BodyType.INDIVIDUALENTERPRISE, individualEnterpriseId);
    }

    @PostMapping("/create-individual-enterprise")
    @ApiOperation(value = "创建个独", notes = "创建个独")
    public R createIndividualEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDTO individualBusinessEnterpriseWebAddDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualEnterpriseService.createIndividualEnterprise(individualBusinessEnterpriseWebAddDto, null);
    }

    @PostMapping("/update-individual-enterprise")
    @ApiOperation(value = "修改个独信息", notes = "修改个独信息")
    public R updateIndividualEnterprise(@Valid @RequestBody IndividualEnterpriseEntity individualEnterprise) {
        return R.status(individualEnterpriseService.updateById(individualEnterprise));
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个独年审信息", notes = "查询个独年审信息")
    public R queryEnterpriseReportList(Query query, @ApiParam(value = "个独ID", required = true) @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
        return individualEnterpriseService.queryEnterpriseReports(query, individualEnterpriseId);
    }

}
