package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.lgyun.system.user.service.IRunCompanyService;
import com.lgyun.system.user.vo.RunCompanyVO;
import com.lgyun.system.user.wrapper.RunCompanyWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/runcompany")
@Validated
@AllArgsConstructor
@Api(value = "平台运营公司（平台方）信息相关接口", tags = "平台运营公司（平台方）信息相关接口")
public class RunCompanyController {
	private static Logger logger = LoggerFactory.getLogger(RunCompanyController.class);

	private IRunCompanyService runCompanyService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody RunCompanyEntity runCompany) {
		return R.status(runCompanyService.save(runCompany));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody RunCompanyEntity runCompany) {
		return R.status(runCompanyService.updateById(runCompany));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<RunCompanyVO> detail(RunCompanyEntity runCompany) {
		RunCompanyEntity detail = runCompanyService.getOne(Condition.getQueryWrapper(runCompany));
		return R.data(RunCompanyWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<RunCompanyVO>> list(RunCompanyEntity runCompany, Query query) {
		IPage<RunCompanyEntity> pages = runCompanyService.page(Condition.getPage(query), Condition.getQueryWrapper(runCompany));
		return R.data(RunCompanyWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(runCompanyService.removeByIds(Func.toLongList(ids)));
	}

}
