package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EmployeeEntity;
import com.lgyun.system.user.service.IEmployeeService;
import com.lgyun.system.user.vo.EmployeeVO;
import com.lgyun.system.user.wrapper.EmployeeWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@RestController
@RequestMapping("/user/employee")
@Api(value = "", tags = "接口")
public class EmployeeController {
	@Autowired
	private IEmployeeService employeeService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入employee")
	public R<EmployeeVO> detail(EmployeeEntity employee) {
		EmployeeEntity detail = employeeService.getOne(Condition.getQueryWrapper(employee));
		return R.data(EmployeeWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入employee")
	public R<IPage<EmployeeVO>> list(EmployeeEntity employee, Query query) {
		IPage<EmployeeEntity> pages = employeeService.page(Condition.getPage(query), Condition.getQueryWrapper(employee));
		return R.data(EmployeeWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入employee")
	public R save(@Valid @RequestBody EmployeeEntity employee) {
		return R.status(employeeService.save(employee));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入employee")
	public R update(@Valid @RequestBody EmployeeEntity employee) {
		return R.status(employeeService.updateById(employee));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Employee")
	public R submit(@Valid @RequestBody EmployeeEntity employee) {
		return R.status(employeeService.saveOrUpdate(employee));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(employeeService.removeByIds(Func.toLongList(ids)));
	}

}
