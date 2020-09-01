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
import lombok.extern.slf4j.Slf4j;
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
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@RestController
@RequestMapping("/web/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "商户基本信息相关接口(管理端)", tags = "商户基本信息相关接口(管理端)")
public class EnterpriseWebController {

    private IEnterpriseService enterpriseService;
    private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/get_service_providers")
    @ApiOperation(value = "获取商户合作服务商", notes = "获取商户合作服务商")
    public R getServiceProviders(Query query, BladeUser bladeUser) {

        log.info("获取商户合作服务商");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return enterpriseService.getServiceProviders(query, enterpriseWorkerEntity.getEnterpriseId());
        } catch (Exception e) {
            log.error("获取商户合作服务商异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/get_enterprise_detail_by_id")
    @ApiOperation(value = "根据商户ID获取商户详情", notes = "根据商户ID获取商户详情")
    public R getEnterpriseDetailById(@ApiParam(value = "商户ID") @NotNull(message = "请输入商户编号") @RequestParam(required = false) Long enterpriseId) {

        log.info("根据商户ID获取商户详情");
        try {
            return enterpriseService.getEnterpriseDetailById(enterpriseId);
        } catch (Exception e) {
            log.error("根据商户ID获取商户详情异常", e);
        }
        return R.fail("查询失败");
    }

}
