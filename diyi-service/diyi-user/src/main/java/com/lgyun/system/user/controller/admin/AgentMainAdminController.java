package com.lgyun.system.user.controller.admin;


import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddAdminAgentMainDTO;
import com.lgyun.system.user.dto.admin.QueryAgentMainDTO;
import com.lgyun.system.user.dto.admin.UpdateAgentMainDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IAgentMainService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 平台端---合伙人管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/admin/agentmain")
@Validated
@AllArgsConstructor
@Api(value = "平台端---渠道管理模块相关接口", tags = "平台端---渠道管理模块相关接口")
public class AgentMainAdminController {
    private IAdminService adminService;
    private IAgentMainService agentMainService;

    @GetMapping("/query-agent-main-list")
    @ApiOperation(value = "查询所有渠道商", notes = "查询所有个渠道商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentMainId", value = "渠道商编号", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "enterpriseName", value = "渠道商名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
    })
    public R queryAgentMainList(QueryAgentMainDTO queryAgentMainDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return agentMainService.getAgentMainList(Condition.getPage(query.setDescs("create_time")), queryAgentMainDTO);
    }

    @PostMapping("/modify-illegal")
    @ApiOperation(value = "修改渠道商状态", notes = "修改渠道商状态")
    public R modifyIllegal(@NotNull(message = "请选择渠道商")@RequestParam(required = false) Long agentMainId,
                           @NotNull(message = "请选择渠道商状态")@RequestParam(required = false) AccountState accountState, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateIllegal(agentMainId, accountState, adminEntity);
    }

    @PostMapping("/create-agent-main")
    @ApiOperation(value = "添加渠道商信息", notes = "添加渠道商信息")
    public R createAgentMain(@Valid @RequestBody AddAdminAgentMainDTO addAdminAgentMainDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.createAgentMain(addAdminAgentMainDTO, adminEntity);
    }

    @PostMapping("/modify-agent-main")
    @ApiOperation(value = "编辑渠道商信息", notes = "编辑渠道商信息")
    public R modifyAgentMain(@Valid @RequestBody UpdateAgentMainDTO updateAgentMainDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateAgentMain(updateAgentMainDTO, adminEntity);
    }

    @PostMapping("/modify-cooperation")
    @ApiOperation(value = "开启关闭合作", notes = "开启关闭合作")
    public R modifyOpenCooperation(@ApiParam("渠道商Id")@NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentProviderId,
                                   @ApiParam("渠道商合作状态") @NotNull(message = "请输入合作状态") @RequestParam(required = false) CooperateStatus cooperateStatus, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateAgentProvider(agentProviderId, cooperateStatus, adminEntity);
    }

    @GetMapping("/query-agent-main-service-provider")
    @ApiOperation(value = "查询可以匹配的服务商", notes = "查询可以匹配的服务商")
    public R queryTaxBureauServiceProvider(@ApiParam("服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return agentMainService.queryAgentMainServiceProvider(serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-agent-main-service-provider")
    @ApiOperation(value = "给渠道商添加匹配服务商", notes = "给渠道商添加匹配服务商")
    public R addAgentMainServiceProvider(@ApiParam("服务商ID字符集，每个ID直接用英文逗号隔开") @NotNull(message = "请选择匹配的服务商！") @RequestParam(required = false) String serviceProviderIds,
                                         @ApiParam("渠道商ID") @NotNull(message = "渠道商ID不能为空！！") @RequestParam(required = false) Long agentMainId, BladeUser bladeUser) {
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.addAgentMainServiceProvider(serviceProviderIds, agentMainId, adminEntity);
    }
}
