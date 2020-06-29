package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.MakerEnterpriseVO;
import com.lgyun.system.user.wrapper.MakerEnterpriseWrapper;
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
@RequestMapping("/makerenterprise")
@Api(value = "创客和外包企业的关联关系相关接口", tags = "创客和外包企业的关联关系相关接口")
public class MakerEnterpriseController {
	@Autowired
	private IMakerEnterpriseService makerEnterpriseService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入makerEnterprise")
	public R<MakerEnterpriseVO> detail(MakerEnterpriseEntity makerEnterprise) {
		MakerEnterpriseEntity detail = makerEnterpriseService.getOne(Condition.getQueryWrapper(makerEnterprise));
		return R.data(MakerEnterpriseWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入makerEnterprise")
	public R<IPage<MakerEnterpriseVO>> list(MakerEnterpriseEntity makerEnterprise, Query query) {
		IPage<MakerEnterpriseEntity> pages = makerEnterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(makerEnterprise));
		return R.data(MakerEnterpriseWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入makerEnterprise")
	public R save(@Valid @RequestBody MakerEnterpriseEntity makerEnterprise) {
		return R.status(makerEnterpriseService.save(makerEnterprise));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入makerEnterprise")
	public R update(@Valid @RequestBody MakerEnterpriseEntity makerEnterprise) {
		return R.status(makerEnterpriseService.updateById(makerEnterprise));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入MakerEnterprise")
	public R submit(@Valid @RequestBody MakerEnterpriseEntity makerEnterprise) {
		return R.status(makerEnterpriseService.saveOrUpdate(makerEnterprise));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(makerEnterpriseService.removeByIds(Func.toLongList(ids)));
	}

}
