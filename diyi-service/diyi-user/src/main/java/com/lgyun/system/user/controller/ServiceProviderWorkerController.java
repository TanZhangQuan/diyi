package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.wrapper.ServiceProviderWorkerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 服务商员工表控制器
 *
 * @author liangfeihu
 * @since 2020-08-13 17:05:17
 */
@Slf4j
@RestController
@RequestMapping("/serviceproviderworker")
@Validated
@AllArgsConstructor
@Api(value = "服务商员工表相关接口", tags = "服务商员工表相关接口")
public class ServiceProviderWorkerController {

	private IServiceProviderWorkerService serviceProviderWorkerService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody ServiceProviderWorkerEntity serviceProviderWorker) {
		return R.status(serviceProviderWorkerService.save(serviceProviderWorker));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody ServiceProviderWorkerEntity serviceProviderWorker) {
		return R.status(serviceProviderWorkerService.updateById(serviceProviderWorker));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(ServiceProviderWorkerEntity serviceProviderWorker) {
		ServiceProviderWorkerEntity detail = serviceProviderWorkerService.getOne(Condition.getQueryWrapper(serviceProviderWorker));
		return R.data(ServiceProviderWorkerWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(ServiceProviderWorkerEntity serviceProviderWorker, Query query) {
		IPage<ServiceProviderWorkerEntity> pages = serviceProviderWorkerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(serviceProviderWorker));
		return R.data(ServiceProviderWorkerWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(serviceProviderWorkerService.removeByIds(Func.toLongList(ids)));
	}

}
