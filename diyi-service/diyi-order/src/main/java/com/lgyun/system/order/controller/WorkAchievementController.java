package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.WorkAchievementEntity;
import com.lgyun.system.order.service.IWorkAchievementService;
import com.lgyun.system.order.vo.WorkAchievementVO;
import com.lgyun.system.order.wrapper.WorkAchievementWrapper;
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
 * @since 2020-06-29 14:46:14
 */
@RestController
@RequestMapping("/workAchievement")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "工作成果", tags = "工作成果")
public class WorkAchievementController {
	private Logger logger = LoggerFactory.getLogger(WorkAchievementController.class);
	private final IWorkAchievementService workAchievementService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入workAchievement")
	public R<WorkAchievementVO> detail(WorkAchievementEntity workAchievement) {
		WorkAchievementEntity detail = workAchievementService.getOne(Condition.getQueryWrapper(workAchievement));
		return R.data(WorkAchievementWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入workAchievement")
	public R<IPage<WorkAchievementVO>> list(WorkAchievementEntity workAchievement, Query query) {
		IPage<WorkAchievementEntity> pages = workAchievementService.page(Condition.getPage(query), Condition.getQueryWrapper(workAchievement));
		return R.data(WorkAchievementWrapper.build().pageVO(pages));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入workAchievement")
	public R update(@Valid @RequestBody WorkAchievementEntity workAchievement) {
		return R.status(workAchievementService.updateById(workAchievement));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入WorkAchievement")
	public R submit(@Valid @RequestBody WorkAchievementEntity workAchievement) {
		return R.status(workAchievementService.saveOrUpdate(workAchievement));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(workAchievementService.removeByIds(Func.toLongList(ids)));
	}


	/**
	 * 提交工作成果
	 */
	@PostMapping("/save")
	@ApiOperation(value = "提交工作成果", notes = "传入workAchievement")
	public R save(@Valid @RequestBody WorkAchievementEntity workAchievement) {
		return R.status(workAchievementService.save(workAchievement));
	}
}
