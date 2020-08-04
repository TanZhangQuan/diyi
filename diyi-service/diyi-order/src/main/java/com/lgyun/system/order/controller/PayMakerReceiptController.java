package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.PayMakerReceiptEntity;
import com.lgyun.system.order.service.IPayMakerReceiptService;
import com.lgyun.system.order.wrapper.PayMakerReceiptWrapper;
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
 * @since 2020-08-04 14:20:06
 */
@Slf4j
@RestController
@RequestMapping("/paymakerreceipt")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class PayMakerReceiptController {

	private IPayMakerReceiptService payMakerReceiptService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody PayMakerReceiptEntity payMakerReceipt) {
		return R.status(payMakerReceiptService.save(payMakerReceipt));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody PayMakerReceiptEntity payMakerReceipt) {
		return R.status(payMakerReceiptService.updateById(payMakerReceipt));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(PayMakerReceiptEntity payMakerReceipt) {
		PayMakerReceiptEntity detail = payMakerReceiptService.getOne(Condition.getQueryWrapper(payMakerReceipt));
		return R.data(PayMakerReceiptWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(PayMakerReceiptEntity payMakerReceipt, Query query) {
		IPage<PayMakerReceiptEntity> pages = payMakerReceiptService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(payMakerReceipt));
		return R.data(PayMakerReceiptWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(payMakerReceiptService.removeByIds(Func.toLongList(ids)));
	}

}
