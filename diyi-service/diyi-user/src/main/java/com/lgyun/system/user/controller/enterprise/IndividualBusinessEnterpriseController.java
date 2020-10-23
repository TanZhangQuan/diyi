package com.lgyun.system.user.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/enterprise/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "商户端---个体户管理模块相关接口", tags = "商户端---个体户管理模块相关接口")
public class IndividualBusinessEnterpriseController {

    private IEnterpriseWorkerService enterpriseWorkerService;
    private IIndividualBusinessService individualBusinessService;
    private IEnterpriseReportService enterpriseReportService;

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询当前商户的关联创客的所有个体户", notes = "查询当前商户的所有关联创客的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryIndividualBusinessList(IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualBusinessService.getIndividualBusinessList(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), null, individualBusinessEnterpriseDto);
    }

    @PostMapping("/create-individual-business")
    @ApiOperation(value = "创建个体户", notes = "创建个体户")
    public R createIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDTO individualBusinessEnterpriseWebAddDto, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return individualBusinessService.createIndividualBusiness(individualBusinessEnterpriseWebAddDto, enterpriseWorkerEntity.getEnterpriseId());
    }

    @PostMapping("/update-individual-business")
    @ApiOperation(value = "修改个体户", notes = "修改个体户")
    public R updateIndividualBusiness(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {
        return R.status(individualBusinessService.updateById(individualBusiness));
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个体户年审信息", notes = "查询个体户年审信息")
    public R queryEnterpriseReportList(Query query, @ApiParam(value = "个体户ID") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId) {
        return enterpriseReportService.findByBodyTypeAndBodyId(BodyType.INDIVIDUALBUSINESS, individualBusinessId, query);
    }

}
