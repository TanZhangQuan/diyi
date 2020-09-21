package com.lgyun.system.user.controller.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
import com.lgyun.system.user.service.IServiceProviderMakerService;
import com.lgyun.system.user.wrapper.ServiceProviderMakerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 服务商创客关联表控制器
 *
 * @author tzq
 * @since 2020-08-19 16:01:29
 */
@Slf4j
@RestController
@RequestMapping("/serviceprovidermaker")
@Validated
@AllArgsConstructor
@Api(value = "服务商创客关联表相关接口", tags = "服务商创客关联表相关接口")
public class ServiceProviderMakerController {

	private IServiceProviderMakerService serviceProviderMakerService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody ServiceProviderMakerEntity serviceProviderMaker) {
		return R.status(serviceProviderMakerService.save(serviceProviderMaker));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody ServiceProviderMakerEntity serviceProviderMaker) {
		return R.status(serviceProviderMakerService.updateById(serviceProviderMaker));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(ServiceProviderMakerEntity serviceProviderMaker) {
		ServiceProviderMakerEntity detail = serviceProviderMakerService.getOne(Condition.getQueryWrapper(serviceProviderMaker));
		return R.data(ServiceProviderMakerWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(ServiceProviderMakerEntity serviceProviderMaker, Query query) {
		IPage<ServiceProviderMakerEntity> pages = serviceProviderMakerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(serviceProviderMaker));
		return R.data(ServiceProviderMakerWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(serviceProviderMakerService.removeByIds(Func.toLongList(ids)));
	}

}
