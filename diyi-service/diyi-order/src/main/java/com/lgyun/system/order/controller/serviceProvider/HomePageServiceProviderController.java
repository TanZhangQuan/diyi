package com.lgyun.system.order.controller.serviceProvider;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.TimeType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping("/service-provider/home-page")
@Validated
@AllArgsConstructor
@Api(value = "服务商端---首页管理模块相关接口", tags = "服务商端---首页管理模块相关接口")
public class HomePageServiceProviderController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-service-provider-transaction")
    @ApiOperation(value = "查询当前服务商首页交易情况数据", notes = "查询当前服务商首页交易情况数据")
    public R queryServiceProviderTransaction(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryServiceProviderTransaction(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/query-total-sub-trade")
    @ApiOperation(value = "查询服务商总包+分包流水", notes = "查询服务商总包+分包流水")
    public R queryTotalSubTrade(@ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                                @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubTrade(null, serviceProviderWorkerEntity.getServiceProviderId(), null, timeType, beginDate, endDate);
    }

    @GetMapping("/query-crowd-trade")
    @ApiOperation(value = "查询服务商众包/众采流水", notes = "查询服务商众包/众采流水")
    public R queryCrowdTrade(@ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                             @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                             @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                             @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                             @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = userClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdTrade(null, serviceProviderWorkerEntity.getServiceProviderId(), null, timeType, beginDate, endDate);
    }

}
