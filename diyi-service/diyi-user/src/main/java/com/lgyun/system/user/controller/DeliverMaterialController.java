package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.DeliverMaterialEntity;
import com.lgyun.system.user.service.IDeliverMaterialService;
import com.lgyun.system.user.vo.DeliverMaterialVO;
import com.lgyun.system.user.wrapper.DeliverMaterialWrapper;
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
@RequestMapping("/user/delivermaterial")
@Api(value = "创客交付材料信息信息相关接口", tags = "创客交付材料信息信息相关接口")
public class DeliverMaterialController {
	@Autowired
	private IDeliverMaterialService deliverMaterialService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入deliverMaterial")
	public R<DeliverMaterialVO> detail(DeliverMaterialEntity deliverMaterial) {
		DeliverMaterialEntity detail = deliverMaterialService.getOne(Condition.getQueryWrapper(deliverMaterial));
		return R.data(DeliverMaterialWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入deliverMaterial")
	public R<IPage<DeliverMaterialVO>> list(DeliverMaterialEntity deliverMaterial, Query query) {
		IPage<DeliverMaterialEntity> pages = deliverMaterialService.page(Condition.getPage(query), Condition.getQueryWrapper(deliverMaterial));
		return R.data(DeliverMaterialWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入deliverMaterial")
	public R save(@Valid @RequestBody DeliverMaterialEntity deliverMaterial) {
		return R.status(deliverMaterialService.save(deliverMaterial));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入deliverMaterial")
	public R update(@Valid @RequestBody DeliverMaterialEntity deliverMaterial) {
		return R.status(deliverMaterialService.updateById(deliverMaterial));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入DeliverMaterial")
	public R submit(@Valid @RequestBody DeliverMaterialEntity deliverMaterial) {
		return R.status(deliverMaterialService.saveOrUpdate(deliverMaterial));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deliverMaterialService.removeByIds(Func.toLongList(ids)));
	}

}
