package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/service-provider/individual-business")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---个体户管理模块相关接口", tags = "服务商端---个体户管理模块相关接口")
public class IndividualBusinessServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IIndividualBusinessService individualBusinessService;
    private IEnterpriseReportService enterpriseReportService;

    @GetMapping("/query-individual-business-list")
    @ApiOperation(value = "查询当前服务商关联的所有个体户", notes = "查询当前服务商关联的所有个体户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryIndividualBusinessList(IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualBusinessService.getIndividualBusinessList(Condition.getPage(query.setDescs("create_time")), null, serviceProviderWorkerEntity.getServiceProviderId(), individualBusinessEnterpriseDto);
    }

    @PostMapping("/update-ibstate")
    @ApiOperation(value = "修改个体户状态", notes = "修改个体户状态")
    public R updateIbstate(@ApiParam(value = "个体户ID", required = true) @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualBusinessId, @ApiParam(value = "个体户状态") @NotNull(message = "请选择个体户状态") @RequestParam(required = false) Ibstate ibstate, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualBusinessService.updateIbstate(serviceProviderWorkerEntity.getServiceProviderId(), individualBusinessId, ibstate);
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
