package com.lgyun.system.user.controller.service_provider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.wrapper.ServiceProviderWorkerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 服务商员工表控制器
 *
 * @author tzq
 * @since 2020-08-13 17:05:17
 */
@RestController
@RequestMapping("/web/service_provider_worker")
@Validated
@AllArgsConstructor
@Api(value = "服务商员工表相关接口(管理端)", tags = "服务商员工表相关接口(管理端)")
public class ServiceProviderWorkerWebController {

    private IServiceProviderWorkerService serviceProviderWorkerService;

    @GetMapping("/current-detail")
    @ApiOperation(value = "查询当前服务商员工详情", notes = "查询当前服务商员工详情")
    public R currentDetail(BladeUser bladeUser) {
        //查询当前创客
        R<ServiceProviderWorkerEntity> result = serviceProviderWorkerService.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return R.data(ServiceProviderWorkerWrapper.build().entityVO(serviceProviderWorkerEntity));
    }

}
