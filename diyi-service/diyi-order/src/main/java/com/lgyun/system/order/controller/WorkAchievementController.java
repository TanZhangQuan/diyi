package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.WorkAchievementEntity;
import com.lgyun.system.order.service.IWorkAchievementService;
import com.lgyun.system.order.wrapper.WorkAchievementWrapper;
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
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@RestController
@RequestMapping("/workachievement")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class WorkAchievementController {

	private IWorkAchievementService workAchievementService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody WorkAchievementEntity workAchievement) {
		return R.status(workAchievementService.save(workAchievement));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody WorkAchievementEntity workAchievement) {
		return R.status(workAchievementService.updateById(workAchievement));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(WorkAchievementEntity workAchievement) {
		WorkAchievementEntity detail = workAchievementService.getOne(Condition.getQueryWrapper(workAchievement));
		return R.data(WorkAchievementWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(WorkAchievementEntity workAchievement, Query query) {
		IPage<WorkAchievementEntity> pages = workAchievementService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(workAchievement));
		return R.data(WorkAchievementWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(workAchievementService.removeByIds(Func.toLongList(ids)));
	}

}
