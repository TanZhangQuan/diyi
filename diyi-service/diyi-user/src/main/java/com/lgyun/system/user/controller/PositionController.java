package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.PositionEntity;
import com.lgyun.system.user.service.IPositionService;
import com.lgyun.system.user.vo.PositionVO;
import com.lgyun.system.user.wrapper.PositionWrapper;
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
@RequestMapping("/position")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "外包岗位的基本信息相关接口", tags = "外包岗位的基本信息相关接口")
public class PositionController {
	private Logger logger = LoggerFactory.getLogger(PositionController.class);

	private final IPositionService positionService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody PositionEntity position) {
		return R.status(positionService.save(position));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody PositionEntity position) {
		return R.status(positionService.updateById(position));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<PositionVO> detail(PositionEntity position) {
		PositionEntity detail = positionService.getOne(Condition.getQueryWrapper(position));
		return R.data(PositionWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<PositionVO>> list(PositionEntity position, Query query) {
		IPage<PositionEntity> pages = positionService.page(Condition.getPage(query), Condition.getQueryWrapper(position));
		return R.data(PositionWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(positionService.removeByIds(Func.toLongList(ids)));
	}

}
