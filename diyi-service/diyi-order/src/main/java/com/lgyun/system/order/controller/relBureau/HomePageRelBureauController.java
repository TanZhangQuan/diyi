package com.lgyun.system.order.controller.relBureau;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.TimeType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.RelBureauEntity;
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
@RequestMapping("/rel-bureau/home-page")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---首页管理模块相关接口", tags = "相关局端---首页管理模块相关接口")
public class HomePageRelBureauController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-rel-bureau-transaction")
    @ApiOperation(value = "查询相关局-服务商交易情况数据", notes = "查询相关局-服务商交易情况数据")
    public R queryRelBureauTransaction(BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryRelBureauTransaction(relBureauEntity.getId());
    }

    @GetMapping("/query-total-sub-trade")
    @ApiOperation(value = "查询相关局总包+分包流水", notes = "查询相关局总包+分包流水")
    public R queryTotalSubTrade(@ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                                @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryTotalSubTrade(null, null, relBureauEntity.getId(), timeType, beginDate, endDate);
    }

    @GetMapping("/query-crowd-trade")
    @ApiOperation(value = "查询相关局众包/众采流水", notes = "查询相关局众包/众采流水")
    public R queryCrowdTrade(@ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                             @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                             @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                             @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                             @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdTrade(null, null, relBureauEntity.getId(), timeType, beginDate, endDate);
    }

    @GetMapping("/query-total-sub-list")
    @ApiOperation(value = "查询相关局总包+分包列表", notes = "查询相关局总包+分包列表")
    public R queryTotalSublist(Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return payEnterpriseService.queryRelBureauTotalSublist(relBureauEntity.getId(), Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/query-crowd-list")
    @ApiOperation(value = "查询相关局众包/众采列表", notes = "查询相关局众包/众采列表")
    public R queryCrowdTrade(Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = userClient.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return selfHelpInvoiceService.queryRelBureauCrowdList(relBureauEntity.getId(), Condition.getPage(query.setDescs("create_time")));
    }

}
