package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.PaysheetEntity;
import com.lgyun.system.order.service.IPaysheetService;
import com.lgyun.system.order.vo.PaysheetVO;
import com.lgyun.system.order.wrapper.PaysheetWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-06 14:14:06
 */
@RestController
@RequestMapping("/user/paysheet")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class PaysheetController {
	private static Logger logger = LoggerFactory.getLogger(PaysheetController.class);

	private IPaysheetService paysheetService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody PaysheetEntity paysheet) {
		return R.status(paysheetService.save(paysheet));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody PaysheetEntity paysheet) {
		return R.status(paysheetService.updateById(paysheet));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<PaysheetVO> detail(PaysheetEntity paysheet) {
		PaysheetEntity detail = paysheetService.getOne(Condition.getQueryWrapper(paysheet));
		return R.data(PaysheetWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<PaysheetVO>> list(PaysheetEntity paysheet, Query query) {
		IPage<PaysheetEntity> pages = paysheetService.page(Condition.getPage(query), Condition.getQueryWrapper(paysheet));
		return R.data(PaysheetWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(paysheetService.removeByIds(Func.toLongList(ids)));
	}

}
