package com.lgyun.system.user.controller.agentMain;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderListDTO;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.service.IAgentMainWorkerService;
import com.lgyun.system.user.service.IServiceProviderService;
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
@RequestMapping("/agent-main/service-provider")
@Validated
@AllArgsConstructor
@Api(value = "渠道商端---服务商管理模块相关接口", tags = "渠道商端---服务商管理模块相关接口")
public class ServiceProviderAgentMainController {

    private IAgentMainWorkerService agentMainWorkerService;
    private IServiceProviderService serviceProviderService;

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询渠道商的所有服务商", notes = "查询渠道商的所有服务商")
    public R queryServiceProviderList(ServiceProviderListDTO serviceProviderListDTO, Query query, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return serviceProviderService.queryServiceProviderListAgentMain(agentMainWorkerEntity.getAgentMainId(), serviceProviderListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-service-provider-detail")
    @ApiOperation(value = "查询服务商详情", notes = "查询服务商详情")
    public R queryServiceProviderDetail(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.queryServiceProviderDetailAgentMain(serviceProviderId);
    }

}
