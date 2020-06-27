package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.MakerVO;
import com.lgyun.system.user.wrapper.MakerWrapper;
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
@RequestMapping("/user/maker")
@Api(value = "", tags = "接口")
public class MakerController {
	@Autowired
	private IMakerService makerService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入maker")
	public R<MakerVO> detail(MakerEntity maker) {
		MakerEntity detail = makerService.getOne(Condition.getQueryWrapper(maker));
		return R.data(MakerWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入maker")
	public R<IPage<MakerVO>> list(MakerEntity maker, Query query) {
		IPage<MakerEntity> pages = makerService.page(Condition.getPage(query), Condition.getQueryWrapper(maker));
		return R.data(MakerWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入maker")
	public R save(@Valid @RequestBody MakerEntity maker) {
		return R.status(makerService.save(maker));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入maker")
	public R update(@Valid @RequestBody MakerEntity maker) {
		return R.status(makerService.updateById(maker));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Maker")
	public R submit(@Valid @RequestBody MakerEntity maker) {
		return R.status(makerService.saveOrUpdate(maker));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(makerService.removeByIds(Func.toLongList(ids)));
	}

}
