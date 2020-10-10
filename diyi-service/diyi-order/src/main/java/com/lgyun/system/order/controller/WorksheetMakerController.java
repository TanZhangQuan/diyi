package com.lgyun.system.order.controller;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.service.IWorksheetMakerService;
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

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-18 19:44:52
 */
@RestController
@RequestMapping("/worksheetmaker")
@Validated
@AllArgsConstructor
@Api(value = "工单创客关联相关接口", tags = "工单创客关联相关接口")
public class WorksheetMakerController {

    private IWorksheetMakerService worksheetMakerService;

    @GetMapping("/get_by_pay_enterprise_id")
    @ApiOperation(value = "根据支付清单ID查询创客工单关联", notes = "根据支付清单ID查询创客工单关联")
    public R getByPayEnterpriseId(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query) {
        return worksheetMakerService.getByPayEnterpriseId(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
    }

}
