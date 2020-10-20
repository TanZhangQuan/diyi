package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户端---首页管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/enterprise/home-page")
@Validated
@AllArgsConstructor
@Api(value = "商户端---首页管理模块相关接口", tags = "商户端---首页管理模块相关接口")
public class HomePageEnterpriseController {

    private IUserClient iUserClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/transaction_by_enterprise")
    @ApiOperation(value = "查询当前商户首页交易情况数据", notes = "查询当前商户首页交易情况数据")
    public R transactionByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.transactionByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_day_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包今日流水", notes = "查询当前商户总包+分包今日流水")
    public R queryTotalSubDayTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubDayTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_week_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包本周流水", notes = "查询当前商户总包+分包本周流水")
    public R queryTotalSubWeekTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubWeekTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_month_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包本月流水", notes = "查询当前商户总包+分包本月流水")
    public R queryTotalSubMonthTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubMonthTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_total_sub_year_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户总包+分包全年流水", notes = "查询当前商户总包+分包全年流水")
    public R queryTotalSubYearTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubYearTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_crowd_day_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包/众采今日流水", notes = "查询当前商户众包/众采今日流水")
    public R queryCrowdDayTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdDayTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_crowd_week_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包/众采本周流水", notes = "查询当前商户众包/众采本周流水")
    public R queryCrowdWeekTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdWeekTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_crowd_month_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包/众采本月流水", notes = "查询当前商户众包/众采本月流水")
    public R queryCrowdMonthTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdMonthTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query_crowd_year_trade_by_enterprise")
    @ApiOperation(value = "查询当前商户众包/众采年流水", notes = "查询当前商户众包/众采年流水")
    public R queryCrowdYearTradeByEnterprise(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = iUserClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdYearTradeByEnterprise(enterpriseWorkerEntity.getEnterpriseId());
    }

}
