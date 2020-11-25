package com.lgyun.system.user.controller.agentMain;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.service.IAgentMainService;
import com.lgyun.system.user.service.IAgentMainWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/agent-main/basic-info")
@Validated
@AllArgsConstructor
@Api(value = "渠道商端---渠道商基本信息管理模块相关接口", tags = "渠道商端---渠道商基本信息管理模块相关接口")
public class BasicInfoAgentMainController {

    private IAgentMainService agentMainService;
    private IAgentMainWorkerService agentMainWorkerService;

    @GetMapping("/query-agent-main-info")
    @ApiOperation(value = "查询渠道商基本信息", notes = "查询渠道商基本信息")
    public R queryAgentMainInfo(BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return agentMainService.queryEnterpriseInfo(agentMainWorkerEntity.getAgentMainId());
    }

    @PostMapping("/update-agent-main-info")
    @ApiOperation(value = "修改渠道商基本信息", notes = "修改渠道商基本信息")
    public R updateBasicInfo(@ApiParam("企业网址") @RequestParam(required = false) String enterpriseUrl, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return agentMainService.updateEnterpriseUrl(agentMainWorkerEntity.getAgentMainId(), enterpriseUrl);
    }

    @GetMapping("/query-contact")
    @ApiOperation(value = "查询当前渠道商所有联系人", notes = "查询当前渠道商所有联系人")
    public R queryContact(BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return agentMainService.queryContact(agentMainWorkerEntity.getAgentMainId());
    }

    @PostMapping("/update-contact")
    @ApiOperation(value = "修改当前渠道商联系人", notes = "修改当前渠道商联系人")
    public R updateContacts(@Valid @RequestBody ContactsInfoDTO contactsInfoDTO, BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return agentMainService.updateContact(agentMainWorkerEntity.getAgentMainId(), contactsInfoDTO);
    }

    @GetMapping("/query-invoice")
    @ApiOperation(value = "查询当前渠道商的开票信息", notes = "查询当前渠道商的开票信息")
    public R queryeInvoice(BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return agentMainService.queryeInvoice(agentMainWorkerEntity.getAgentMainId());
    }

}
