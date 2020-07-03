package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.WorksheetAttentionEntity;
import com.lgyun.system.user.service.IWorksheetAttentionService;
import com.lgyun.system.user.vo.WorksheetAttentionVO;
import com.lgyun.system.user.wrapper.WorksheetAttentionWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@RestController
@RequestMapping("/worksheetattention")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "工单信息相关接口", tags = "工单信息相关接口")
public class WorksheetAttentionController {
	private Logger logger = LoggerFactory.getLogger(WorksheetAttentionController.class);

	private final IWorksheetAttentionService worksheetAttentionService;

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入worksheetAttention")
	public R<WorksheetAttentionVO> detail(WorksheetAttentionEntity worksheetAttention) {
		WorksheetAttentionEntity detail = worksheetAttentionService.getOne(Condition.getQueryWrapper(worksheetAttention));
		return R.data(WorksheetAttentionWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入worksheetAttention")
	public R<IPage<WorksheetAttentionVO>> list(WorksheetAttentionEntity worksheetAttention, Query query) {
		IPage<WorksheetAttentionEntity> pages = worksheetAttentionService.page(Condition.getPage(query), Condition.getQueryWrapper(worksheetAttention));
		return R.data(WorksheetAttentionWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入worksheetAttention")
	public R save(@Valid @RequestBody WorksheetAttentionEntity worksheetAttention) {
		return R.status(worksheetAttentionService.save(worksheetAttention));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入worksheetAttention")
	public R update(@Valid @RequestBody WorksheetAttentionEntity worksheetAttention) {
		return R.status(worksheetAttentionService.updateById(worksheetAttention));
	}

	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入WorksheetAttention")
	public R submit(@Valid @RequestBody WorksheetAttentionEntity worksheetAttention) {
		return R.status(worksheetAttentionService.saveOrUpdate(worksheetAttention));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(worksheetAttentionService.removeByIds(Func.toLongList(ids)));
	}

}
