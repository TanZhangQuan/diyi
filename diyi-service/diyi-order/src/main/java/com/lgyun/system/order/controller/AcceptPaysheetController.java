package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.service.IAcceptPaysheetService;
import com.lgyun.system.order.vo.AcceptPaysheetVO;
import com.lgyun.system.order.wrapper.AcceptPaysheetWrapper;
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
 * @since 2020-07-17 14:38:25
 */
@Slf4j
@RestController
@RequestMapping("/user/acceptpaysheet")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class AcceptPaysheetController {

	private IAcceptPaysheetService acceptPaysheetService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody AcceptPaysheetEntity acceptPaysheet) {
		return R.status(acceptPaysheetService.save(acceptPaysheet));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody AcceptPaysheetEntity acceptPaysheet) {
		return R.status(acceptPaysheetService.updateById(acceptPaysheet));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<AcceptPaysheetVO> detail(AcceptPaysheetEntity acceptPaysheet) {
		AcceptPaysheetEntity detail = acceptPaysheetService.getOne(Condition.getQueryWrapper(acceptPaysheet));
		return R.data(AcceptPaysheetWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<AcceptPaysheetVO>> list(AcceptPaysheetEntity acceptPaysheet, Query query) {
		IPage<AcceptPaysheetEntity> pages = acceptPaysheetService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(acceptPaysheet));
		return R.data(AcceptPaysheetWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(acceptPaysheetService.removeByIds(Func.toLongList(ids)));
	}

}
