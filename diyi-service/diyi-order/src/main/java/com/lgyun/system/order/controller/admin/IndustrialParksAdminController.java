package com.lgyun.system.order.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/industrial-parks-bureau")
@Validated
@AllArgsConstructor
@Api(value = "平台端---在线经济产业园区管理模块相关接口", tags = "平台端---在线经济产业园区管理模块相关接口")
public class IndustrialParksAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;

    @PostMapping("/transaction-by-bureau-service-provider")
    @ApiOperation(value = "查询当前产业园区所有匹配的服务商交易情况数据", notes = "查询当前产业园区所有匹配的服务商交易情况数据")
    public R transactionByBureauServiceProvider(@ApiParam("产业园区ID") @NotNull(message = "产业园区ID不能为空！") @RequestParam(required = false) Long bureauId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.transactionByBureauServiceProvider(bureauId);
    }


    @PostMapping("/transaction-by-bureau-service-provider-info")
    @ApiOperation(value = "查询产业园区匹配的服务商基本信息及交易金额", notes = "查询产业园区匹配的服务商基本信息及交易金额")
    public R transactionByBureauServiceProviderInfo(@ApiParam("产业园区ID") @NotNull(message = "产业园区ID不能为空！") @RequestParam(required = false) Long bureauId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.transactionByBureauServiceProviderInfo(bureauId, Condition.getPage(query.setDescs("create_time")));
    }
}