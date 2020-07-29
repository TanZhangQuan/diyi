package com.lgyun.system.user.controller;

import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.wrapper.EnterpriseProviderWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
@Slf4j
@RestController
@RequestMapping("/order/enterpriseprovider")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class EnterpriseProviderController {

	private IEnterpriseProviderService enterpriseProviderService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody EnterpriseProviderEntity enterpriseProvider) {
		return R.status(enterpriseProviderService.save(enterpriseProvider));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody EnterpriseProviderEntity enterpriseProvider) {
		return R.status(enterpriseProviderService.updateById(enterpriseProvider));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(EnterpriseProviderEntity enterpriseProvider) {
		EnterpriseProviderEntity detail = enterpriseProviderService.getOne(Condition.getQueryWrapper(enterpriseProvider));
		return R.data(EnterpriseProviderWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(EnterpriseProviderEntity enterpriseProvider, Query query) {
		IPage<EnterpriseProviderEntity> pages = enterpriseProviderService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(enterpriseProvider));
		return R.data(EnterpriseProviderWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(enterpriseProviderService.removeByIds(Func.toLongList(ids)));
	}

}
