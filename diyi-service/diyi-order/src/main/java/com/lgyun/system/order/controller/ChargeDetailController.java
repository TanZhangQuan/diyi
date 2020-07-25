package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.ChargeDetailEntity;
import com.lgyun.system.order.service.IChargeDetailService;
import com.lgyun.system.order.wrapper.ChargeDetailWrapper;
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
@RequestMapping("/user/chargedetail")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class ChargeDetailController {

	private IChargeDetailService chargeDetailService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody ChargeDetailEntity chargeDetail) {
		return R.status(chargeDetailService.save(chargeDetail));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody ChargeDetailEntity chargeDetail) {
		return R.status(chargeDetailService.updateById(chargeDetail));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(ChargeDetailEntity chargeDetail) {
		ChargeDetailEntity detail = chargeDetailService.getOne(Condition.getQueryWrapper(chargeDetail));
		return R.data(ChargeDetailWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(ChargeDetailEntity chargeDetail, Query query) {
		IPage<ChargeDetailEntity> pages = chargeDetailService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(chargeDetail));
		return R.data(ChargeDetailWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(chargeDetailService.removeByIds(Func.toLongList(ids)));
	}

}
