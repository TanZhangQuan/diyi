package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.PayEntity;
import com.lgyun.system.order.service.IPayService;
import com.lgyun.system.order.wrapper.PayWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:07
 */
@Slf4j
@RestController
@RequestMapping("/user/pay")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class PayController {

	private IPayService payService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody PayEntity pay) {
		return R.status(payService.save(pay));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody PayEntity pay) {
		return R.status(payService.updateById(pay));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(PayEntity pay) {
		PayEntity detail = payService.getOne(Condition.getQueryWrapper(pay));
		return R.data(PayWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(PayEntity pay, Query query) {
		IPage<PayEntity> pages = payService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(pay));
		return R.data(PayWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(payService.removeByIds(Func.toLongList(ids)));
	}

}
