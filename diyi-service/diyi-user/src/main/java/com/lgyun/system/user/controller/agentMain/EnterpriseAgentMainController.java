package com.lgyun.system.user.controller.agentMain;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.CreateEnterpriseDTO;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.dto.UpdateEnterpriseDTO;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.service.IAgentMainWorkerService;
import com.lgyun.system.user.service.IEnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/agent-main/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "渠道商端---商户管理模块相关接口", tags = "渠道商端---商户管理模块相关接口")
public class EnterpriseAgentMainController {

    private IEnterpriseService enterpriseService;
    private IAgentMainWorkerService agentMainWorkerService;

    @PostMapping("/create-enterprise")
    @ApiOperation(value = "添加商户", notes = "添加商户")
    public R createEnterprise(@Valid @RequestBody CreateEnterpriseDTO createEnterpriseDTO, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return enterpriseService.createEnterprise(createEnterpriseDTO, agentMainWorkerEntity.getAgentMainId(), null);
    }

    @PostMapping("/update-enterprise")
    @ApiOperation(value = "编辑商户", notes = "编辑商户")
    public R updateEnterprise(@Valid @RequestBody UpdateEnterpriseDTO updateEnterpriseDTO, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return enterpriseService.updateEnterprise(updateEnterpriseDTO, agentMainWorkerEntity.getAgentMainId(), null);
    }

    @GetMapping("/query-enterprise-update-detail")
    @ApiOperation(value = "查询编辑商户详情", notes = "查询编辑商户详情")
    public R queryEnterpriseUpdateDetail(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseUpdateDetail(enterpriseId);
    }

    @GetMapping("/query-enterprise-list")
    @ApiOperation(value = "查询渠道商的所有商户", notes = "查询渠道商的所有商户")
    public R queryEnterpriseList(EnterpriseListDTO enterpriseListDTO, Query query, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return enterpriseService.queryEnterpriseListAgentMain(agentMainWorkerEntity.getAgentMainId(), enterpriseListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-enterprise-detail")
    @ApiOperation(value = "查询商户详情", notes = "查询商户详情")
    public R queryEnterpriseDetail(@ApiParam(value = "商户") @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseDetailAgentMain(enterpriseId);
    }

}
