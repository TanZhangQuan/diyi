package com.lgyun.system.order.controller.RelBureau;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rel-bureau/home-page")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---首页管理模块相关接口", tags = "相关局端---首页管理模块相关接口")
public class HomePageRelBureauController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @PostMapping("/query-rel-bureau-service-provider-transaction")
    @ApiOperation(value = "查询相关局-服务商交易情况数据", notes = "查询相关局-服务商交易情况数据")
    public R queryRelBureauServiceProviderTransaction(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryRelBureauServiceProviderTransaction(relBureauEntity.getId());
    }

    @GetMapping("/query-total-sub-day-trade")
    @ApiOperation(value = "查询相关局-服务商总包+分包今日流水", notes = "查询相关局-服务商总包+分包今日流水")
    public R queryTotalSubDayTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryTotalSubDayTradeByServiceProvider(null, relBureauEntity.getId());
    }

    @GetMapping("/query-total-sub-week-trade")
    @ApiOperation(value = "查询相关局-服务商总包+分包本周流水", notes = "查询相关局-服务商总包+分包本周流水")
    public R queryTotalSubWeekTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryTotalSubWeekTradeByServiceProvider(null, relBureauEntity.getId());
    }

    @GetMapping("/query-total-sub-month-trade")
    @ApiOperation(value = "查询相关局-服务商总包+分包本月流水", notes = "查询相关局-服务商总包+分包本月流水")
    public R queryTotalSubMonthTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryTotalSubMonthTradeByServiceProvider(null, relBureauEntity.getId());
    }

    @GetMapping("/query-total-sub-year-trade")
    @ApiOperation(value = "查询相关局-服务商总包+分包全年流水", notes = "查询相关局-服务商总包+分包全年流水")
    public R queryTotalSubYearTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryTotalSubYearTradeByServiceProvider(null, relBureauEntity.getId());
    }

    @GetMapping("/query-crowd-day-trade")
    @ApiOperation(value = "查询相关局-服务商众包包今日流水", notes = "查询相关局-服务商众包包今日流水")
    public R queryCrowdDayTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdDayTradeByServiceProvider(null, relBureauEntity.getId());
    }

    @GetMapping("/query-crowd-week-trade")
    @ApiOperation(value = "查询相关局-服务商众包包本周流水", notes = "查询相关局-服务商众包包本周流水")
    public R queryCrowdWeekTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdWeekTradeByServiceProvider(null, relBureauEntity.getId());
    }

    @GetMapping("/query-crowd-month-trade")
    @ApiOperation(value = "查询相关局-服务商众包/众采本月流水", notes = "查询相关局-服务商众包/众采本月流水")
    public R queryCrowdMonthTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdMonthTradeByServiceProvider(null, relBureauEntity.getId());
    }

    @GetMapping("/query-crowd-year-trade")
    @ApiOperation(value = "查询相关局-服务商众包/众采年流水", notes = "查询相关局-服务商众包/众采年流水")
    public R queryCrowdYearTrade(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdYearTradeByServiceProvider(null, relBureauEntity.getId());
    }

}
