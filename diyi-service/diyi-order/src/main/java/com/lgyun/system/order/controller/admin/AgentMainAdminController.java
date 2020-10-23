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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/agent-main")
@Validated
@AllArgsConstructor
@Api(value = "平台端---渠道管理模块相关接口", tags = "平台端---渠道管理模块相关接口")
public class AgentMainAdminController {

    private IUserClient userClient;
    private IPayEnterpriseService payEnterpriseService;

    @GetMapping("/query-agent-main-transaction")
    @ApiOperation(value = "查询渠道商交易数据", notes = "查询渠道商交易数据")
    public R queryAgentMainTransaction(@ApiParam(value = "渠道商ID", required = true) @NotNull(message = "请输入渠道商编号") @RequestParam(required = false) Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.transactionByAgentMainId(agentMainId);
    }

    @GetMapping("/query-agent-main-service-provider")
    @ApiOperation(value = "查询渠道商已经匹配好的服务商", notes = "查询渠道商已经匹配好的服务商")
    public R queryAgentMainServiceProvider(@ApiParam(value = "渠道商ID", required = true) @NotNull(message = "请输入渠道商编号") @RequestParam(required = false) Long agentMainId, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = userClient.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return payEnterpriseService.getCooperativeServiceProvider(Condition.getPage(query.setDescs("create_time")), agentMainId);
    }


}
