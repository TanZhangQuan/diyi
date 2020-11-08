package com.lgyun.system.order.controller.enterprise;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
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
@RequestMapping("/enterprise/natural-person-maker")
@Validated
@AllArgsConstructor
@Api(value = "商户端---自然人创客管理模块相关接口", tags = "商户端---自然人创客管理模块相关接口")
public class NaturalPersonMakerEnterpriseController {

    private IUserClient userClient;
    private IWorksheetService worksheetService;
    private IPayMakerService payMakerService;

    @GetMapping("/query-worksheet-list")
    @ApiOperation(value = "查询工单", notes = "查询工单")
    public R queryWorksheetList(@ApiParam(value = "创客") @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return worksheetService.getWorksheetDetailsByMaker(null, makerId, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @GetMapping("/query-pay-maker-list")
    @ApiOperation(value = "查询创客支付明细", notes = "查询创客支付明细")
    public R queryPayMakerList(@ApiParam(value = "创客") @NotNull(message = "请选择创客") @RequestParam(required = false) Long makerId, Query query, BladeUser bladeUser) {
        //查询当前商户员工
        R<EnterpriseWorkerEntity> result = userClient.currentEnterpriseWorker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return payMakerService.queryPayMakerListByMaker(makerId, Condition.getPage(query.setDescs("create_time")));
    }

}
