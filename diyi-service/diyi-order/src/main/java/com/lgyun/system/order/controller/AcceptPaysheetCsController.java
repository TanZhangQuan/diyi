package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.AcceptPaysheetCsEntity;
import com.lgyun.system.order.service.IAcceptPaysheetCsService;
import com.lgyun.system.order.wrapper.AcceptPaysheetCsWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 众包交付支付验收单表控制器
 *
 * @author liangfeihu
 * @since 2020-08-05 10:43:29
 */
@Slf4j
@RestController
@RequestMapping("/acceptpaysheetcs")
@Validated
@AllArgsConstructor
@Api(value = "众包交付支付验收单表相关接口", tags = "众包交付支付验收单表相关接口")
public class AcceptPaysheetCsController {

	private IAcceptPaysheetCsService acceptPaysheetCsService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody AcceptPaysheetCsEntity acceptPaysheetCs) {
		return R.status(acceptPaysheetCsService.save(acceptPaysheetCs));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody AcceptPaysheetCsEntity acceptPaysheetCs) {
		return R.status(acceptPaysheetCsService.updateById(acceptPaysheetCs));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(AcceptPaysheetCsEntity acceptPaysheetCs) {
		AcceptPaysheetCsEntity detail = acceptPaysheetCsService.getOne(Condition.getQueryWrapper(acceptPaysheetCs));
		return R.data(AcceptPaysheetCsWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(AcceptPaysheetCsEntity acceptPaysheetCs, Query query) {
		IPage<AcceptPaysheetCsEntity> pages = acceptPaysheetCsService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(acceptPaysheetCs));
		return R.data(AcceptPaysheetCsWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(acceptPaysheetCsService.removeByIds(Func.toLongList(ids)));
	}

}
