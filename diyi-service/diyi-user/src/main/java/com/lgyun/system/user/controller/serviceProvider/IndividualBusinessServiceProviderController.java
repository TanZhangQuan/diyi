package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseUpdateServiceProviderDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseReportService;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public R queryIndividualBusinessList(IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualBusinessService.queryIndividualBusinessList(null, serviceProviderWorkerEntity.getServiceProviderId(), individualBusinessEnterpriseListDto, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-individual-business-detail")
    @ApiOperation(value = "查询个体户详情", notes = "查询个体户详情")
    public R queryIndividualBusinessDetail(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryIndividualBusinessDetail(individualBusinessId);
    }

    @GetMapping("/query-update-individual-business-detail")
    @ApiOperation(value = "查询编辑个体户详情", notes = "查询编辑个体户详情")
    public R queryUpdateIndividualBusinessDetail(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return individualBusinessService.queryUpdateIndividualBusinessDetailServiceProvider(individualBusinessId);
    }

    @PostMapping("/cancel-individual-business")
    @ApiOperation(value = "注销个体户", notes = "注销个体户")
    public R cancelIndividualBusiness(@ApiParam(value = "个体户", required = true) @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualBusinessService.cancelIndividualBusiness(serviceProviderWorkerEntity.getServiceProviderId(), individualBusinessId);
    }

    @PostMapping("/update-individual-business")
    @ApiOperation(value = "编辑个体户", notes = "编辑个体户")
    public R updateIndividualBusiness(@Valid @RequestBody IndividualBusinessEnterpriseUpdateServiceProviderDTO individualBusinessEnterpriseUpdateServiceProviderDTO, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return individualBusinessService.updateIndividualBusinessServiceProvider(individualBusinessEnterpriseUpdateServiceProviderDTO, serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query-enterprise-report-list")
    @ApiOperation(value = "查询个体户年审信息", notes = "查询个体户年审信息")
    public R queryEnterpriseReportList(@ApiParam(value = "个体户") @NotNull(message = "请选择个体户") @RequestParam(required = false) Long individualBusinessId, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseReportService.findByBodyTypeAndBodyId(BodyType.INDIVIDUALBUSINESS, individualBusinessId, query);
    }

}
