package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EmployeeEntity;
import com.lgyun.system.user.service.IEmployeeService;
import com.lgyun.system.user.wrapper.EmployeeWrapper;
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
@RequestMapping("/employee")
@Validated
@AllArgsConstructor
@Api(value = "平台工作人员的基本信息相关接口", tags = "平台工作人员的基本信息相关接口")
public class EmployeeController {

	private IEmployeeService employeeService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody EmployeeEntity employee) {
		return R.status(employeeService.save(employee));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody EmployeeEntity employee) {
		return R.status(employeeService.updateById(employee));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(EmployeeEntity employee) {
		EmployeeEntity detail = employeeService.getOne(Condition.getQueryWrapper(employee));
		return R.data(EmployeeWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(EmployeeEntity employee, Query query) {
		IPage<EmployeeEntity> pages = employeeService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(employee));
		return R.data(EmployeeWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(employeeService.removeByIds(Func.toLongList(ids)));
	}

}
