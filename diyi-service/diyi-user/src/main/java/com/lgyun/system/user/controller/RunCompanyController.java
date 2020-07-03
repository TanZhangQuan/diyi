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
@RequestMapping("/runcompany")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "平台运营公司（平台方）信息相关接口", tags = "平台运营公司（平台方）信息相关接口")
public class RunCompanyController {
	private Logger logger = LoggerFactory.getLogger(RunCompanyController.class);

	private final IRunCompanyService runCompanyService;

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入runCompany")
	public R<RunCompanyVO> detail(RunCompanyEntity runCompany) {
		RunCompanyEntity detail = runCompanyService.getOne(Condition.getQueryWrapper(runCompany));
		return R.data(RunCompanyWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入runCompany")
	public R<IPage<RunCompanyVO>> list(RunCompanyEntity runCompany, Query query) {
		IPage<RunCompanyEntity> pages = runCompanyService.page(Condition.getPage(query), Condition.getQueryWrapper(runCompany));
		return R.data(RunCompanyWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入runCompany")
	public R save(@Valid @RequestBody RunCompanyEntity runCompany) {
		return R.status(runCompanyService.save(runCompany));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入runCompany")
	public R update(@Valid @RequestBody RunCompanyEntity runCompany) {
		return R.status(runCompanyService.updateById(runCompany));
	}

	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入RunCompany")
	public R submit(@Valid @RequestBody RunCompanyEntity runCompany) {
		return R.status(runCompanyService.saveOrUpdate(runCompany));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(runCompanyService.removeByIds(Func.toLongList(ids)));
	}

}
