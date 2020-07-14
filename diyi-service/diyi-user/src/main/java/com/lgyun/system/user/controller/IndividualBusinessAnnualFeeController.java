package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.IndividualBusinessAnnualFeeEntity;
import com.lgyun.system.user.service.IIndividualBusinessAnnualFeeService;
import com.lgyun.system.user.vo.IndividualBusinessAnnualFeeVO;
import com.lgyun.system.user.wrapper.IndividualBusinessAnnualFeeWrapper;
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
 * @since 2020-07-02 17:44:02
 */
@Slf4j
@RestController
@RequestMapping("/individual-business-annual-fee")
@Validated
@AllArgsConstructor
@Api(value = "个体户年费相关接口", tags = "个体户年费相关接口")
public class IndividualBusinessAnnualFeeController {

	private IIndividualBusinessAnnualFeeService individualBusinessAnnualFeeService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
		return R.status(individualBusinessAnnualFeeService.save(individualBusinessAnnualFee));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
		return R.status(individualBusinessAnnualFeeService.updateById(individualBusinessAnnualFee));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<IndividualBusinessAnnualFeeVO> detail(IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
		IndividualBusinessAnnualFeeEntity detail = individualBusinessAnnualFeeService.getOne(Condition.getQueryWrapper(individualBusinessAnnualFee));
		return R.data(IndividualBusinessAnnualFeeWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<IndividualBusinessAnnualFeeVO>> list(IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee, Query query) {
		IPage<IndividualBusinessAnnualFeeEntity> pages = individualBusinessAnnualFeeService.page(Condition.getPage(query), Condition.getQueryWrapper(individualBusinessAnnualFee));
		return R.data(IndividualBusinessAnnualFeeWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(individualBusinessAnnualFeeService.removeByIds(Func.toLongList(ids)));
	}

}
