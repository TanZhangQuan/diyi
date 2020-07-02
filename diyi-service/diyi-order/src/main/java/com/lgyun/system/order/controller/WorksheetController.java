package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.order.vo.WorksheetVO;
import com.lgyun.system.order.wrapper.WorksheetWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  控制器
 *
 * @author jun
 * @since 2020-06-29 10:39:06
 */
@RestController
@RequestMapping("/worksheet")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "工单", tags = "工单")
public class WorksheetController {
	private Logger logger = LoggerFactory.getLogger(WorksheetController.class);
	private final IWorksheetService worksheetService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入worksheet")
	public R<WorksheetVO> detail(WorksheetEntity worksheet) {
		WorksheetEntity detail = worksheetService.getOne(Condition.getQueryWrapper(worksheet));
		return R.data(WorksheetWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入worksheet")
	public R<IPage<WorksheetVO>> list(WorksheetEntity worksheet, Query query) {
		IPage<WorksheetEntity> pages = worksheetService.page(Condition.getPage(query), Condition.getQueryWrapper(worksheet));
		return R.data(WorksheetWrapper.build().pageVO(pages));
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入worksheet")
	public R save(@Valid @RequestBody WorksheetEntity worksheet) {
		return R.status(worksheetService.save(worksheet));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入worksheet")
	public R update(@Valid @RequestBody WorksheetEntity worksheet) {
		return R.status(worksheetService.updateById(worksheet));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Worksheet")
	public R submit(@Valid @RequestBody WorksheetEntity worksheet) {
		return R.status(worksheetService.saveOrUpdate(worksheet));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(worksheetService.removeByIds(Func.toLongList(ids)));
	}

}
