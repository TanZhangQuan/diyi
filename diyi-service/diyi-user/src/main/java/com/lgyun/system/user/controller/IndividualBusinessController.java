package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.vo.IndividualBusinessVO;
import com.lgyun.system.user.wrapper.IndividualBusinessWrapper;
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
 * @since 2020-06-26 17:21:06
 */
@RestController
@RequestMapping("/user/individualbusiness")
@Api(value = "", tags = "接口")
public class IndividualBusinessController {
	@Autowired
	private IIndividualBusinessService individualBusinessService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入individualBusiness")
	public R<IndividualBusinessVO> detail(IndividualBusinessEntity individualBusiness) {
		IndividualBusinessEntity detail = individualBusinessService.getOne(Condition.getQueryWrapper(individualBusiness));
		return R.data(IndividualBusinessWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入individualBusiness")
	public R<IPage<IndividualBusinessVO>> list(IndividualBusinessEntity individualBusiness, Query query) {
		IPage<IndividualBusinessEntity> pages = individualBusinessService.page(Condition.getPage(query), Condition.getQueryWrapper(individualBusiness));
		return R.data(IndividualBusinessWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入individualBusiness")
	public R save(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {
		return R.status(individualBusinessService.save(individualBusiness));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入individualBusiness")
	public R update(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {
		return R.status(individualBusinessService.updateById(individualBusiness));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入IndividualBusiness")
	public R submit(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {
		return R.status(individualBusinessService.saveOrUpdate(individualBusiness));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(individualBusinessService.removeByIds(Func.toLongList(ids)));
	}

}
