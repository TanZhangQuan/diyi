package com.lgyun.system.user.controller;

import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.service.IOnlineAgreementTemplateService;
import com.lgyun.system.user.vo.OnlineAgreementTemplateVO;
import com.lgyun.system.user.wrapper.OnlineAgreementTemplateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 控制器
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Slf4j
@RestController
@RequestMapping("/order/onlineagreementtemplate")
@Validated
@AllArgsConstructor
@Api(value = "平台在线协议模板相关接口", tags = "平台在线协议模板相关接口")
public class OnlineAgreementTemplateController {

	private IOnlineAgreementTemplateService onlineAgreementTemplateService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody OnlineAgreementTemplateEntity onlineAgreementTemplate) {
		return R.status(onlineAgreementTemplateService.save(onlineAgreementTemplate));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody OnlineAgreementTemplateEntity onlineAgreementTemplate) {
		return R.status(onlineAgreementTemplateService.updateById(onlineAgreementTemplate));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<OnlineAgreementTemplateVO> detail(OnlineAgreementTemplateEntity onlineAgreementTemplate) {
		OnlineAgreementTemplateEntity detail = onlineAgreementTemplateService.getOne(Condition.getQueryWrapper(onlineAgreementTemplate));
		return R.data(OnlineAgreementTemplateWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<OnlineAgreementTemplateVO>> list(OnlineAgreementTemplateEntity onlineAgreementTemplate, Query query) {
		IPage<OnlineAgreementTemplateEntity> pages = onlineAgreementTemplateService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(onlineAgreementTemplate));
		return R.data(OnlineAgreementTemplateWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(onlineAgreementTemplateService.removeByIds(Func.toLongList(ids)));
	}

}
