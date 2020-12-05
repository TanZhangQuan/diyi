package com.lgyun.system.user.controller.admin;


import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.CreateAgentMainDTO;
import com.lgyun.system.user.dto.AgentMainListDTO;
import com.lgyun.system.user.dto.UpdateAgentMainDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 平台端---渠道商管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/admin/agent-main")
@Validated
@AllArgsConstructor
@Api(value = "平台端---渠道商管理模块相关接口", tags = "平台端---渠道商管理模块相关接口")
public class AgentMainAdminController {

    private IAdminService adminService;
    private IAgentMainService agentMainService;
    private IEnterpriseService enterpriseService;
    private IServiceProviderService serviceProviderService;
    private IAgentMainEnterpriseService agentMainEnterpriseService;
    private IAgentMainServiceProviderService agentMainServiceProviderService;

    @PostMapping("/create-agent-main")
    @ApiOperation(value = "添加渠道商", notes = "添加渠道商")
    public R createAgentMain(@Valid @RequestBody CreateAgentMainDTO createAgentMainDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return agentMainService.createAgentMain(createAgentMainDTO, adminEntity);
    }

    @PostMapping("/update-agent-main")
    @ApiOperation(value = "编辑渠道商", notes = "编辑渠道商")
    public R updateAgentMain(@Valid @RequestBody UpdateAgentMainDTO updateAgentMainDTO, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();

        return agentMainService.updateAgentMain(updateAgentMainDTO, adminEntity);
    }

    @GetMapping("/query-agent-main-update-detail")
    @ApiOperation(value = "查询编辑渠道商详情", notes = "查询编辑渠道商详情")
    public R queryAgentMainUpdateDetail(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainService.queryAgentMainUpdateDetail(agentMainId);
    }

    @GetMapping("/query-agent-main-list")
    @ApiOperation(value = "查询所有渠道商", notes = "查询所有个渠道商")
    public R queryAgentMainList(AgentMainListDTO agentMainListDTO, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainService.getAgentMainListAdmin(agentMainListDTO, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/update-agent-main-state")
    @ApiOperation(value = "更改渠道商状态", notes = "更改渠道商状态")
    public R updateAgentMainState(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                  @ApiParam(value = "渠道商状态", required = true) @NotNull(message = "请选择渠道商状态") @RequestParam(required = false) AccountState agentMainState, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainService.updateAgentMainState(agentMainId, agentMainState);
    }

    @GetMapping("/query-service-provider-id-and-name-list")
    @ApiOperation(value = "查询所有服务商编号姓名", notes = "查询所有服务商编号姓名")
    public R queryServiceProviderIdAndNameList(@ApiParam(value = "服务商名称") @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return serviceProviderService.queryServiceProviderIdAndNameList(null, serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/match-service-provider")
    @ApiOperation(value = "渠道商匹配服务商", notes = "渠道商匹配服务商")
    public R matchServiceProvider(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                  @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                  @ApiParam(value = "分配说明") @RequestParam(required = false) String matchDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainServiceProviderService.relevanceAgentMainServiceProvider(agentMainId, serviceProviderId, matchDesc);
    }

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询渠道商合作服务商", notes = "查询渠道商合作服务商")
    public R queryCooperationServiceProviderList(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                                 @ApiParam(value = "服务商名称", required = true) @RequestParam(required = false) String serviceProviderName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainServiceProviderService.queryCooperationServiceProviderList(agentMainId, serviceProviderName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/update-agent-main-service-provider-cooperation-status")
    @ApiOperation(value = "更改渠道商服务商合作关系", notes = "更改渠道商服务商合作关系")
    public R updateAgentMainServiceProviderCooperationStatus(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                                             @ApiParam(value = "服务商", required = true) @NotNull(message = "请选择服务商") @RequestParam(required = false) Long serviceProviderId,
                                                             @ApiParam(value = "合作状态", required = true) @NotNull(message = "请选择合作状态") @RequestParam(required = false) CooperateStatus cooperateStatus, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainServiceProviderService.updateCooperationStatus(agentMainId, serviceProviderId, cooperateStatus);
    }

    @GetMapping("/query-enterprise-id-and-name-list")
    @ApiOperation(value = "查询所有商户编号姓名", notes = "查询所有商户编号姓名")
    public R queryEnterpriseIdAndNameList(@ApiParam(value = "商户名称") @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return enterpriseService.queryEnterpriseIdAndNameList(null, null, enterpriseName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/match-enterprise")
    @ApiOperation(value = "渠道商匹配商户", notes = "渠道商匹配商户")
    public R matchEnterprise(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                  @ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                  @ApiParam(value = "分配说明") @RequestParam(required = false) String matchDesc, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainEnterpriseService.relevanceAgentMainEnterprise(agentMainId, enterpriseId, matchDesc);
    }

    @GetMapping("/query-cooperation-enterprise-list")
    @ApiOperation(value = "查询渠道商合作商户", notes = "查询渠道商合作商户")
    public R queryCooperationEnterpriseList(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                            @ApiParam(value = "商户名称", required = true) @RequestParam(required = false) String enterpriseName, Query query, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainEnterpriseService.queryCooperationEnterpriseList(agentMainId, enterpriseName, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @PostMapping("/update-agent-main-enterprise-cooperation-status")
    @ApiOperation(value = "更改渠道商商户合作关系", notes = "更改渠道商商户合作关系")
    public R updateAgentMainEnterpriseCooperationStatus(@ApiParam(value = "渠道商", required = true) @NotNull(message = "请选择渠道商") @RequestParam(required = false) Long agentMainId,
                                     @ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId,
                                     @ApiParam(value = "合作状态", required = true) @NotNull(message = "请选择合作状态") @RequestParam(required = false) CooperateStatus cooperateStatus, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agentMainEnterpriseService.updateCooperationStatus(agentMainId, enterpriseId, cooperateStatus);
    }

}
