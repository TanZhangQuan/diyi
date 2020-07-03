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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@RestController
@RequestMapping("/individual_business_annual_fee")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "个体户年费相关接口", tags = "个体户年费相关接口")
public class IndividualBusinessAnnualFeeController {
	private Logger logger = LoggerFactory.getLogger(IndividualBusinessAnnualFeeController.class);

	private final IIndividualBusinessAnnualFeeService individualBusinessAnnualFeeService;

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入individualBusinessAnnualFee")
	public R<IndividualBusinessAnnualFeeVO> detail(IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
		IndividualBusinessAnnualFeeEntity detail = individualBusinessAnnualFeeService.getOne(Condition.getQueryWrapper(individualBusinessAnnualFee));
		return R.data(IndividualBusinessAnnualFeeWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入individualBusinessAnnualFee")
	public R<IPage<IndividualBusinessAnnualFeeVO>> list(IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee, Query query) {
		IPage<IndividualBusinessAnnualFeeEntity> pages = individualBusinessAnnualFeeService.page(Condition.getPage(query), Condition.getQueryWrapper(individualBusinessAnnualFee));
		return R.data(IndividualBusinessAnnualFeeWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入individualBusinessAnnualFee")
	public R save(@Valid @RequestBody IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
		return R.status(individualBusinessAnnualFeeService.save(individualBusinessAnnualFee));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入individualBusinessAnnualFee")
	public R update(@Valid @RequestBody IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
		return R.status(individualBusinessAnnualFeeService.updateById(individualBusinessAnnualFee));
	}

	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入IndividualBusinessAnnualFee")
	public R submit(@Valid @RequestBody IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
		return R.status(individualBusinessAnnualFeeService.saveOrUpdate(individualBusinessAnnualFee));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(individualBusinessAnnualFeeService.removeByIds(Func.toLongList(ids)));
	}

}
