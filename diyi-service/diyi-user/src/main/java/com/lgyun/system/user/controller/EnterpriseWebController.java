package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
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
@RequestMapping("/web/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户基本信息相关接口(管理端)", tags = "商户基本信息相关接口(管理端)")
public class EnterpriseWebController {

    private IEnterpriseService enterpriseService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/query-service-provider-list")
    @ApiOperation(value = "查询商户合作服务商", notes = "查询商户合作服务商")
    public R getServiceProviders(Query query, BladeUser bladeUser) {
            //查询当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return enterpriseService.getServiceProviders(query, enterpriseWorkerEntity.getEnterpriseId());
    }

    @GetMapping("/query-enterprise-detail")
    @ApiOperation(value = "查询商户详情", notes = "查询商户详情")
    public R queryEnterpriseDetail(@ApiParam(value = "商户", required = true) @NotNull(message = "请选择商户") @RequestParam(required = false) Long enterpriseId) {
            return enterpriseService.getEnterpriseDetailById(enterpriseId);
    }

}
