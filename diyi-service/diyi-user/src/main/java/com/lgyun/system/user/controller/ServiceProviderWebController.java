package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IServiceProviderService;
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
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@RestController
@RequestMapping("/web/service_provider")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class ServiceProviderWebController {

	private IServiceProviderService serviceProviderService;
	private IEnterpriseWorkerService enterpriseWorkerService;

	@GetMapping("/get_service_providers_by_enterprise_id")
	@ApiOperation(value = "获取当前商户合作服务商", notes = "获取当前商户合作服务商")
	public R getServiceProvidersByEnterpriseId(@ApiParam(value = "支付清单编号") @NotNull(message = "请输入支付清单编号") @RequestParam(required = false) Long payEnterpriseId, Query query, BladeUser bladeUser) {

		log.info("获取当前商户合作服务商");
		try {
			//获取当前商户员工
			R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
			if (!(result.isSuccess())) {
				return result;
			}
			EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

			return serviceProviderService.getServiceProvidersByEnterpriseId(payEnterpriseId, Condition.getPage(query.setDescs("create_time")));
		} catch (Exception e) {
			log.error("获取当前商户合作服务商异常", e);
		}
		return R.fail("查询失败");
	}

}
