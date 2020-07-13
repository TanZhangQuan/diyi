package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.vo.EnterpriseVO;
import com.lgyun.system.user.wrapper.EnterpriseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@RestController
@RequestMapping("/enterprise")
@Validated
@AllArgsConstructor
@Api(value = "外包企业（发包方）的基本信息相关接口", tags = "外包企业（发包方）的基本信息相关接口")
public class EnterpriseController {

	private IEnterpriseService enterpriseService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody EnterpriseEntity enterprise) {
		return R.status(enterpriseService.save(enterprise));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody EnterpriseEntity enterprise) {
		return R.status(enterpriseService.updateById(enterprise));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<EnterpriseVO> detail(EnterpriseEntity enterprise) {
		EnterpriseEntity detail = enterpriseService.getOne(Condition.getQueryWrapper(enterprise));
		return R.data(EnterpriseWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<EnterpriseVO>> list(EnterpriseEntity enterprise, Query query) {
		IPage<EnterpriseEntity> pages = enterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(enterprise));
		return R.data(EnterpriseWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(enterpriseService.removeByIds(Func.toLongList(ids)));
	}

}
