package com.lgyun.system.user.controller.admin;


import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddAdminAgentMainDTO;
import com.lgyun.system.user.dto.admin.QueryAgentMainDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IAgentMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 平台端---合同管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/admin/agentmain")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合同管理模块相关接口", tags = "平台端---合同管理模块相关接口")
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
        return agentMainService.getAgentMainList(Condition.getPage(query.setDescs("create_time")),  queryAgentMainDTO);
    }

    @PostMapping("/modify-illegal")
    @ApiOperation(value = "非法", notes = "非法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentMainId", value = "渠道商编号", paramType = "query", dataType = "long")
    })
    public R modifyIllegal(Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateIllegal(agentMainId,adminEntity);
    }

    @PostMapping("/modify-freeze")
    @ApiOperation(value = "冻结", notes = "冻结")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentMainId", value = "渠道商编号", paramType = "query", dataType = "long")
    })
    public R modifyFreeze(Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateFreeze(agentMainId,adminEntity);
    }

    @PostMapping("/modify-normal")
    @ApiOperation(value = "正常", notes = "正常")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentMainId", value = "渠道商编号", paramType = "query", dataType = "long")
    })
    public R modifyNormal(Long agentMainId, BladeUser bladeUser) {
        //查询当前管理员
        R<AdminEntity> result = adminService.currentAdmin(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AdminEntity adminEntity = result.getData();
        return agentMainService.updateNormal(agentMainId,adminEntity);
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
        return agentMainService.createAgentMain(addAdminAgentMainDTO,adminEntity);
    }


}
