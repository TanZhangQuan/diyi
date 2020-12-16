package com.lgyun.system.order.controller.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.TimeType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
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
@RequestMapping("/enterprise/home-page")
@Validated
@AllArgsConstructor
@Api(value = "商户端---首页管理模块相关接口", tags = "商户端---首页管理模块相关接口")
public class HomePageEnterpriseController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;
    private ISelfHelpInvoiceService selfHelpInvoiceService;

    @GetMapping("/query-enterprise-transaction")
    @ApiOperation(value = "查询当前商户首页交易情况数据", notes = "查询当前商户首页交易情况数据")
    public R queryEnterpriseTransaction(BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryEnterpriseTransaction(enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query-total-sub-trade")
    @ApiOperation(value = "查询商户总包+分包流水", notes = "查询商户总包+分包流水")
    public R queryTotalSubTrade(@ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                                @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return payEnterpriseService.queryTotalSubTrade(enterpriseWorkerEntity.getEnterpriseId(), null, null, timeType, beginDate, endDate);
    }

    @GetMapping("/query-crowd-trade")
    @ApiOperation(value = "查询商户众包/众采流水", notes = "查询商户众包/众采流水")
    public R queryCrowdTrade(@ApiParam(value = "时间类型", required = true) @NotNull(message = "请选择时间类型") @RequestParam(required = false) TimeType timeType,
                             @ApiParam(value = "开始时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                             @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                             @ApiParam(value = "结束时间", required = true) @JsonFormat(pattern = "yyyy-MM-dd")
                             @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

        return selfHelpInvoiceService.queryCrowdTrade(enterpriseWorkerEntity.getEnterpriseId(), null, null, timeType, beginDate, endDate);
    }

}
