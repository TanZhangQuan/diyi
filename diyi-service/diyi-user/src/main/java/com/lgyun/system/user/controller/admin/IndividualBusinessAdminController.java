package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import io.swagger.annotations.*;
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

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询所有个体户", notes = "查询所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryIndividualBusinessList(IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.getIndividualBusinessList(Condition.getPage(query.setDescs("create_time")), null, null, individualBusinessEnterpriseDto);
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个体户年审信息", notes = "查询个体户年审信息")
    public R queryEnterpriseReportList(Query query, @ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findByBodyTypeAndBodyId(query, BodyType.INDIVIDUALBUSINESS, individualBusinessId);
    }

    @PostMapping("/create-individual-business")
    @ApiOperation(value = "创建个体户", notes = "创建个体户")
    public R createIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDTO individualBusinessEnterpriseWebAddDto, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.createIndividualBusiness(individualBusinessEnterpriseWebAddDto, null);
    }

}
