package com.lgyun.system.order.controller.agentMain;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.user.dto.PayEnterpriseListSimpleDTO;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
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

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-service-provider-transaction")
    @ApiOperation(value = "查询服务商交易数据", notes = "查询服务商交易数据")
    public R queryServiceProviderTransaction(BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return payEnterpriseService.queryServiceProviderTransaction(agentMainWorkerEntity.getId());
    }

    @GetMapping("/query-pay-enterprise-list")
    @ApiOperation(value = "查询服务商总包+分包", notes = "查询服务商总包+分包")
    public R queryPayEnterpriseList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, PayEnterpriseListSimpleDTO payEnterpriseListSimpleDTO, Query query, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseListAgentMain(null, serviceProviderId, payEnterpriseListSimpleDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

}
