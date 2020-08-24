package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@Slf4j
@RestController
@RequestMapping("/web/enterpriseprovider")
@Validated
@AllArgsConstructor
@Api(value = "商户-服务商相关接口(管理端)", tags = "商户-服务商相关接口(管理端)")
public class EnterpriseProviderWebController {

    private IEnterpriseProviderService enterpriseProviderService;
	private IEnterpriseWorkerService enterpriseWorkerService;

    @GetMapping("/get_service_providers_by_enterprise_id")
    @ApiOperation(value = "获取当前商户合作服务商", notes = "获取当前商户合作服务商")
    public R getServiceProvidersByEnterpriseId(String keyWord, Query query, BladeUser bladeUser) {

        log.info("获取当前商户合作服务商");
        try {
            //获取当前商户员工
            R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

            return enterpriseProviderService.getServiceProvidersByEnterpriseId(enterpriseWorkerEntity.getEnterpriseId(), keyWord, Condition.getPage(query.setDescs("create_time")));
        } catch (Exception e) {
            log.error("获取当前商户合作服务商异常", e);
        }
        return R.fail("查询失败");
    }

}
