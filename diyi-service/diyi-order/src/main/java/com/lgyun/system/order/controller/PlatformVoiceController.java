package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.PlatformVoiceEntity;
import com.lgyun.system.order.service.IPlatformVoiceService;
import com.lgyun.system.order.wrapper.PlatformVoiceWrapper;
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
@RequestMapping("/user/platformvoice")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class PlatformVoiceController {

	private IPlatformVoiceService platformVoiceService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody PlatformVoiceEntity platformVoice) {
		return R.status(platformVoiceService.save(platformVoice));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody PlatformVoiceEntity platformVoice) {
		return R.status(platformVoiceService.updateById(platformVoice));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(PlatformVoiceEntity platformVoice) {
		PlatformVoiceEntity detail = platformVoiceService.getOne(Condition.getQueryWrapper(platformVoice));
		return R.data(PlatformVoiceWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(PlatformVoiceEntity platformVoice, Query query) {
		IPage<PlatformVoiceEntity> pages = platformVoiceService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(platformVoice));
		return R.data(PlatformVoiceWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(platformVoiceService.removeByIds(Func.toLongList(ids)));
	}

}
