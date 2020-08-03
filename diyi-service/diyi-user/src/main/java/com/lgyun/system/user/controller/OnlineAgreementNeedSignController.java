package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.service.IOnlineAgreementNeedSignService;
import com.lgyun.system.user.wrapper.OnlineAgreementNeedSignWrapper;
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
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Slf4j
@RestController
@RequestMapping("/onlineagreementneedsign")
@Validated
@AllArgsConstructor
@Api(value = "在线签署清单-签名保存相关接口", tags = "在线签署清单-签名保存相关接口")
public class OnlineAgreementNeedSignController {

	private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody OnlineAgreementNeedSignEntity onlineAgreementNeedSign) {
		return R.status(onlineAgreementNeedSignService.save(onlineAgreementNeedSign));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody OnlineAgreementNeedSignEntity onlineAgreementNeedSign) {
		return R.status(onlineAgreementNeedSignService.updateById(onlineAgreementNeedSign));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(OnlineAgreementNeedSignEntity onlineAgreementNeedSign) {
		OnlineAgreementNeedSignEntity detail = onlineAgreementNeedSignService.getOne(Condition.getQueryWrapper(onlineAgreementNeedSign));
		return R.data(OnlineAgreementNeedSignWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(OnlineAgreementNeedSignEntity onlineAgreementNeedSign, Query query) {
		IPage<OnlineAgreementNeedSignEntity> pages = onlineAgreementNeedSignService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(onlineAgreementNeedSign));
		return R.data(OnlineAgreementNeedSignWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(onlineAgreementNeedSignService.removeByIds(Func.toLongList(ids)));
	}

}
