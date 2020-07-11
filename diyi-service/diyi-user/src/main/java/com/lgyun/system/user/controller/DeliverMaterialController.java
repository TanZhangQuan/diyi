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
 * @since 2020-06-26 17:21:05
 */
@RestController
@RequestMapping("/delivermaterial")
@Validated
@AllArgsConstructor
@Api(value = "创客交付材料信息信息相关接口", tags = "创客交付材料信息信息相关接口")
public class DeliverMaterialController {
	private static Logger logger = LoggerFactory.getLogger(DeliverMaterialController.class);

	private IDeliverMaterialService deliverMaterialService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody DeliverMaterialEntity deliverMaterial) {
		return R.status(deliverMaterialService.save(deliverMaterial));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody DeliverMaterialEntity deliverMaterial) {
		return R.status(deliverMaterialService.updateById(deliverMaterial));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<DeliverMaterialVO> detail(DeliverMaterialEntity deliverMaterial) {
		DeliverMaterialEntity detail = deliverMaterialService.getOne(Condition.getQueryWrapper(deliverMaterial));
		return R.data(DeliverMaterialWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<DeliverMaterialVO>> list(DeliverMaterialEntity deliverMaterial, Query query) {
		IPage<DeliverMaterialEntity> pages = deliverMaterialService.page(Condition.getPage(query), Condition.getQueryWrapper(deliverMaterial));
		return R.data(DeliverMaterialWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deliverMaterialService.removeByIds(Func.toLongList(ids)));
	}

}
