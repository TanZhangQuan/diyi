package com.lgyun.system.user.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-provider/home-page")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---首页管理模块相关接口", tags = "服务商端---首页管理模块相关接口")
public class HomePageServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IEnterpriseServiceProviderService enterpriseProviderService;

    @GetMapping("/query-current-service-provider-detail")
    @ApiOperation(value = "查询当前服务商员工详情", notes = "查询当前服务商员工详情")
    public R queryCurrentServiceProviderDetail(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderWorkerService.queryServiceProviderWorkerDetail(serviceProviderWorkerEntity.getId());
    }

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询当前服务商合作商户", notes = "查询当前服务商合作商户")
    public R queryEnterpriseList(Query query, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return enterpriseProviderService.getEnterprisesByServiceProvider(Condition.getPage(query.setDescs("create_time")), serviceProviderWorkerEntity.getServiceProviderId());
    }

}
