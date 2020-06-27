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
@RequestMapping("/user/enterprise")
@Api(value = "", tags = "接口")
public class EnterpriseController {
	@Autowired
	private IEnterpriseService enterpriseService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入enterprise")
	public R<EnterpriseVO> detail(EnterpriseEntity enterprise) {
		EnterpriseEntity detail = enterpriseService.getOne(Condition.getQueryWrapper(enterprise));
		return R.data(EnterpriseWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入enterprise")
	public R<IPage<EnterpriseVO>> list(EnterpriseEntity enterprise, Query query) {
		IPage<EnterpriseEntity> pages = enterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(enterprise));
		return R.data(EnterpriseWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入enterprise")
	public R save(@Valid @RequestBody EnterpriseEntity enterprise) {
		return R.status(enterpriseService.save(enterprise));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入enterprise")
	public R update(@Valid @RequestBody EnterpriseEntity enterprise) {
		return R.status(enterpriseService.updateById(enterprise));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Enterprise")
	public R submit(@Valid @RequestBody EnterpriseEntity enterprise) {
		return R.status(enterpriseService.saveOrUpdate(enterprise));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(enterpriseService.removeByIds(Func.toLongList(ids)));
	}

}
