package com.lgyun.system.order.controller.serviceProvider;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
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

    private IUserClient iUserClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/web/pay_enterprise/transaction_by_service_provider")
    @ApiOperation(value = "查询当前服务商首页交易情况数据", notes = "查询当前服务商首页交易情况数据")
    public R transactionByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.transactionByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/web/pay_enterprise/query_total_sub_day_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包今日流水", notes = "查询当前服务商总包+分包今日流水")
    public R queryTotalSubDayTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubDayTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/web/pay_enterprise/query_total_sub_week_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包本周流水", notes = "查询当前服务商总包+分包本周流水")
    public R queryTotalSubWeekTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubWeekTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/web/pay_enterprise/query_total_sub_month_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包本月流水", notes = "查询当前服务商总包+分包本月流水")
    public R queryTotalSubMonthTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubMonthTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/web/pay_enterprise/query_total_sub_year_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商总包+分包全年流水", notes = "查询当前服务商总包+分包全年流水")
    public R queryTotalSubYearTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubYearTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/order/webSelfhelpinvoice/query_crowd_day_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包包今日流水", notes = "查询当前服务商众包包今日流水")
    public R queryCrowdDayTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdDayTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/order/webSelfhelpinvoice/query_crowd_week_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包包本周流水", notes = "查询当前服务商众包包本周流水")
    public R queryCrowdWeekTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdWeekTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/order/webSelfhelpinvoice/query_crowd_month_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包/众采本月流水", notes = "查询当前服务商众包/众采本月流水")
    public R queryCrowdMonthTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdMonthTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

    @GetMapping("/order/webSelfhelpinvoice/query_crowd_year_trade_by_service_provider")
    @ApiOperation(value = "查询当前服务商众包/众采年流水", notes = "查询当前服务商众包/众采年流水")
    public R queryCrowdYearTradeByServiceProvider(BladeUser bladeUser) {
        //查询当前服务商员工
        R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdYearTradeByServiceProvider(serviceProviderWorkerEntity.getServiceProviderId());
    }

}