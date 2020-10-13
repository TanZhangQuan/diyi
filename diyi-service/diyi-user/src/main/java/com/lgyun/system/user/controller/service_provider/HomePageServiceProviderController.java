package com.lgyun.system.user.controller.service_provider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务商端---首页管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
//@RequestMapping("/service-provider/home-page")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---首页管理模块相关接口", tags = "服务商端---首页管理模块相关接口")
public class HomePageServiceProviderController {

    private IServiceProviderWorkerService serviceProviderWorkerService;

    @GetMapping("/web/service_provider_worker/current-detail")
    @ApiOperation(value = "查询当前服务商员工详情", notes = "查询当前服务商员工详情")
    public R currentDetail(BladeUser bladeUser) {
        //查询当前创客
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return serviceProviderWorkerService.queryServiceProviderWorkerDetail(serviceProviderWorkerEntity.getId());
    }

}
