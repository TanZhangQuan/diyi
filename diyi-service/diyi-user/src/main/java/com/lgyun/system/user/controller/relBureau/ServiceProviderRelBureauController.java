package com.lgyun.system.user.controller.relBureau;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/rel-bureau/service-provider")
@Validated
@AllArgsConstructor
@Api(value = "相关局端---服务商管理模块相关接口", tags = "相关局端---服务商管理模块相关接口")
public class ServiceProviderRelBureauController {

    private IRelBureauService relBureauService;
    private IServiceProviderService serviceProviderService;

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询所有服务商", notes = "查询所有服务商")
    public R queryServiceProviderList(ServiceProviderListDTO serviceProviderListDTO, Query query, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        RelBureauEntity relBureauEntity = result.getData();

        return serviceProviderService.queryServiceProviderList(null, relBureauEntity.getId(), serviceProviderListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-service-provider-detail")
    @ApiOperation(value = "查询服务商详情", notes = "查询服务商详情")
    public R queryServiceProviderDetail(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, BladeUser bladeUser) {
        //查询当前相关局
        R<RelBureauEntity> result = relBureauService.currentRelBureau(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.queryServiceProviderDetailAgentMain(serviceProviderId);
    }

}
