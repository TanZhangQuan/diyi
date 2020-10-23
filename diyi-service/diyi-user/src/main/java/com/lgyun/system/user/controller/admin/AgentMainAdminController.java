package com.lgyun.system.user.controller.admin;


import com.lgyun.common.api.R;
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
import javax.validation.constraints.NotBlank;
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
    @ApiOperation(value = "非法", notes = "非法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentMainId", value = "渠道商编号", paramType = "query", dataType = "long")
    })
    public R modifyIllegal(@NotBlank(message = "请选择渠道商") Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateIllegal(agentMainId, adminEntity);
    }

    @PostMapping("/modify-freeze")
    @ApiOperation(value = "冻结", notes = "冻结")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentMainId", value = "渠道商编号", paramType = "query", dataType = "long")
    })
    public R modifyFreeze(@NotBlank(message = "请选择渠道商") Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateFreeze(agentMainId, adminEntity);
    }

    @PostMapping("/modify-normal")
    @ApiOperation(value = "正常", notes = "正常")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentMainId", value = "渠道商编号", paramType = "query", dataType = "long")
    })
    public R modifyNormal(@NotBlank(message = "请选择渠道商") Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateNormal(agentMainId, adminEntity);
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

    @PostMapping("/modify-open-cooperation")
    @ApiOperation(value = "开启合作", notes = "开启合作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentProviderId", value = "渠道商&服务商编号", paramType = "query", dataType = "long")
    })
    public R modifyOpenCooperation(@NotBlank(message = "请选择服务商") Long agentProviderId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateOpenAgentProvider(agentProviderId,adminEntity);
    }

    @PostMapping("/modify-suspend-cooperation")
    @ApiOperation(value = "暂停合作合作", notes = "暂停合作合作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentProviderId", value = "渠道商&服务商编号", paramType = "query", dataType = "long")
    })
    public R modifySuspendCooperation(@NotBlank(message = "请选择服务商") Long agentProviderId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateCloseAgentProvider(agentProviderId,adminEntity);
    }

    @GetMapping("/query-agent-main-service-provider")
    @ApiOperation(value = "查询可以匹配的服务商", notes = "查询可以匹配的服务商")
    public R queryTaxBureauServiceProvider(@ApiParam("服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser){
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return agentMainService.queryAgentMainServiceProvider(serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @PostMapping("/add-agent-main-service-provider")
    @ApiOperation(value = "给渠道商添加匹配服务商", notes = "给渠道商添加匹配服务商")
    public R addAgentMainServiceProvider(@ApiParam("服务商ID字符集，每个ID直接用英文逗号隔开") @NotBlank(message = "请选择匹配的服务商！") @RequestParam(required = false) String serviceProviderIds,
                                         @ApiParam("渠道商ID") @NotNull(message = "渠道商ID不能为空！！") @RequestParam(required = false) Long agentMainId, BladeUser bladeUser){
        //查询当前管理
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.addAgentMainServiceProvider(serviceProviderIds,agentMainId,adminEntity);
    }
}
