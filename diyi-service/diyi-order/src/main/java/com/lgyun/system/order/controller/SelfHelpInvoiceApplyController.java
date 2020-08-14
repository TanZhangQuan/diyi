package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceApplyService;
import com.lgyun.system.order.wrapper.SelfHelpInvoiceApplyWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 自助开票申请：记录自助开票主表的申请记录情况控制器
 *
 * @author liangfeihu
 * @since 2020-08-08 10:36:37
 */
@Slf4j
@RestController
@RequestMapping("/selfhelpinvoiceapply")
@Validated
@AllArgsConstructor
@Api(value = "自助开票申请相关接口", tags = "自助开票申请相关接口")
public class SelfHelpInvoiceApplyController {

	private ISelfHelpInvoiceApplyService selfHelpInvoiceApplyService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody SelfHelpInvoiceApplyEntity selfHelpInvoiceApply) {
		return R.status(selfHelpInvoiceApplyService.save(selfHelpInvoiceApply));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody SelfHelpInvoiceApplyEntity selfHelpInvoiceApply) {
		return R.status(selfHelpInvoiceApplyService.updateById(selfHelpInvoiceApply));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(SelfHelpInvoiceApplyEntity selfHelpInvoiceApply) {
		SelfHelpInvoiceApplyEntity detail = selfHelpInvoiceApplyService.getOne(Condition.getQueryWrapper(selfHelpInvoiceApply));
		return R.data(SelfHelpInvoiceApplyWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(SelfHelpInvoiceApplyEntity selfHelpInvoiceApply, Query query) {
		IPage<SelfHelpInvoiceApplyEntity> pages = selfHelpInvoiceApplyService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(selfHelpInvoiceApply));
		return R.data(SelfHelpInvoiceApplyWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(selfHelpInvoiceApplyService.removeByIds(Func.toLongList(ids)));
	}

}
