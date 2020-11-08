package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/service-provider/cooperation-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---合作商户管理模块相关接口", tags = "服务商端---合作商户管理模块相关接口")
public class CooperationEnterpriseServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IEnterpriseServiceProviderService enterpriseProviderService;
    private IEnterpriseService enterpriseService;

    @GetMapping("/query-cooperation-enterprise-list")
    @ApiOperation(value = "查询当前服务商合作商户", notes = "查询当前服务商合作商户")
    public R queryCooperationEnterpriseList(String keyWord, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseProviderService.getEnterprtisesByServiceProviderId(serviceProviderWorkerEntity.getServiceProviderId(), keyWord, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-enterprise-detail")
    @ApiOperation(value = "查询商户详情", notes = "查询商户详情")
    public R queryEnterpriseDetail(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.getEnterpriseDetailById(enterpriseId);
    }

}
