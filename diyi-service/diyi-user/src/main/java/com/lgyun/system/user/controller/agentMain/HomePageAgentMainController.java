package com.lgyun.system.user.controller.agentMain;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.service.IAgentMainWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent-main/home-page")
@Validated
@AllArgsConstructor
@Api(value = "商户端---首页管理模块相关接口", tags = "商户端---首页管理模块相关接口")
public class HomePageAgentMainController {

    private IAgentMainWorkerService agentMainWorkerService;

    @GetMapping("/query-current-agent-main-detail")
    @ApiOperation(value = "查询当前渠道商员工详情", notes = "查询当前渠道商员工详情")
    public R queryCurrentAgentMainDetail(BladeUser bladeUser) {
        //查询当前渠道商员工
        R<AgentMainWorkerEntity> result = agentMainWorkerService.currentAgentMainWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        AgentMainWorkerEntity agentMainWorkerEntity = result.getData();

        return agentMainWorkerService.queryAgentMainWorkerDetail(agentMainWorkerEntity.getId());
    }

}
