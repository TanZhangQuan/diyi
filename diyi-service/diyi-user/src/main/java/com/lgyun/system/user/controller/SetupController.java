package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.SetupEntity;
import com.lgyun.system.user.service.ISetupService;
import com.lgyun.system.user.vo.SetupVO;
import com.lgyun.system.user.wrapper.SetupWrapper;
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
@RequestMapping("/setup")
@Api(value = "特殊配置相关接口", tags = "特殊配置相关接口")
public class SetupController {
	@Autowired
	private ISetupService setupService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入setup")
	public R<SetupVO> detail(SetupEntity setup) {
		SetupEntity detail = setupService.getOne(Condition.getQueryWrapper(setup));
		return R.data(SetupWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入setup")
	public R<IPage<SetupVO>> list(SetupEntity setup, Query query) {
		IPage<SetupEntity> pages = setupService.page(Condition.getPage(query), Condition.getQueryWrapper(setup));
		return R.data(SetupWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入setup")
	public R save(@Valid @RequestBody SetupEntity setup) {
		return R.status(setupService.save(setup));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入setup")
	public R update(@Valid @RequestBody SetupEntity setup) {
		return R.status(setupService.updateById(setup));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Setup")
	public R submit(@Valid @RequestBody SetupEntity setup) {
		return R.status(setupService.saveOrUpdate(setup));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(setupService.removeByIds(Func.toLongList(ids)));
	}

}
