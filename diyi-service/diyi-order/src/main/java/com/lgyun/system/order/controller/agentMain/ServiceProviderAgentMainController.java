package com.lgyun.system.order.controller.agentMain;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IWorksheetService;
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
    private IWorksheetService worksheetService;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-service-provider-transaction")
    @ApiOperation(value = "查询服务商交易数据", notes = "查询服务商交易数据")
    public R queryServiceProviderTransaction(@ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryServiceProviderTransaction(serviceProviderId);
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

    @GetMapping("/query-pay-enterprise-detail")
    @ApiOperation(value = "查询总包支付清单详情", notes = "查询总包支付清单详情")
    public R queryPayEnterpriseDetail(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseDetail(payEnterpriseId);
    }

    @GetMapping("/query-pay-enterprise-express")
    @ApiOperation(value = "查询总包支付清单物流信息", notes = "查询总包支付清单物流信息")
    public R queryPayEnterpriseExpress(@ApiParam(value = "支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.queryPayEnterpriseExpress(payEnterpriseId);
    }

    @GetMapping("/query-worksheet-detail")
    @ApiOperation(value = "查询工单详情", notes = "查询工单详情")
    public R queryTotalSubAcceptPaysheetList(@ApiParam(value = "工单", required = true) @NotNull(message = "请选择工单") @RequestParam(required = false) Long worksheetId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getByWorksheetId(worksheetId);
    }

    @GetMapping("/query-pay-maker-list")
    @ApiOperation(value = "根据支付清单查询分包支付明细", notes = "根据支付清单查询分包支付明细")
    public R queryPayMakerList(@ApiParam(value = "总包支付清单", required = true) @NotNull(message = "请选择总包支付清单") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = userClient.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payEnterpriseService.getPayMakerListByPayEnterprise(payEnterpriseId, Condition.getPage(query.setDescs("t1.create_time")));
    }

}
