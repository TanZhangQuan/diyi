package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@RestController
@RequestMapping("/web/enterpriseprovider")
@Validated
@AllArgsConstructor
@Api(value = "商户-服务商相关接口(管理端)", tags = "商户-服务商相关接口(管理端)")
public class EnterpriseServiceProviderWebController {

    private IEnterpriseServiceProviderService enterpriseProviderService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IServiceProviderWorkerService serviceProviderWorkerService;

    @GetMapping("/get_service_providers_by_enterprise_id")
    @ApiOperation(value = "查询当前商户合作服务商", notes = "查询当前商户合作服务商")
    public R getServiceProvidersByEnterpriseId(String keyWord, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return enterpriseProviderService.getServiceProvidersByEnterpriseId(enterpriseWorkerEntity.getEnterpriseId(), keyWord, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/get_enterprtises_by_service_provider_id")
    @ApiOperation(value = "查询当前服务商合作商户", notes = "查询当前服务商合作商户")
    public R getEnterprtisesByServiceProviderId(String keyWord, Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseProviderService.getEnterprtisesByServiceProviderId(serviceProviderWorkerEntity.getServiceProviderId(), keyWord, Condition.getPage(query.setDescs("create_time")));
    }

}
