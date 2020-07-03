package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.IndividualEnterpriseAnnualFeeEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseAnnualFeeService;
import com.lgyun.system.user.vo.IndividualEnterpriseAnnualFeeVO;
import com.lgyun.system.user.wrapper.IndividualEnterpriseAnnualFeeWrapper;
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
@RequestMapping("/individual_enterprise_annual_fee")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "个独年费相关接口", tags = "个独年费相关接口")
public class IndividualEnterpriseAnnualFeeController {
	private Logger logger = LoggerFactory.getLogger(IndividualEnterpriseAnnualFeeController.class);

	private final IIndividualEnterpriseAnnualFeeService individualEnterpriseAnnualFeeService;

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入individualEnterpriseAnnualFee")
	public R<IndividualEnterpriseAnnualFeeVO> detail(IndividualEnterpriseAnnualFeeEntity individualEnterpriseAnnualFee) {
		IndividualEnterpriseAnnualFeeEntity detail = individualEnterpriseAnnualFeeService.getOne(Condition.getQueryWrapper(individualEnterpriseAnnualFee));
		return R.data(IndividualEnterpriseAnnualFeeWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入individualEnterpriseAnnualFee")
	public R<IPage<IndividualEnterpriseAnnualFeeVO>> list(IndividualEnterpriseAnnualFeeEntity individualEnterpriseAnnualFee, Query query) {
		IPage<IndividualEnterpriseAnnualFeeEntity> pages = individualEnterpriseAnnualFeeService.page(Condition.getPage(query), Condition.getQueryWrapper(individualEnterpriseAnnualFee));
		return R.data(IndividualEnterpriseAnnualFeeWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入individualEnterpriseAnnualFee")
	public R save(@Valid @RequestBody IndividualEnterpriseAnnualFeeEntity individualEnterpriseAnnualFee) {
		return R.status(individualEnterpriseAnnualFeeService.save(individualEnterpriseAnnualFee));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入individualEnterpriseAnnualFee")
	public R update(@Valid @RequestBody IndividualEnterpriseAnnualFeeEntity individualEnterpriseAnnualFee) {
		return R.status(individualEnterpriseAnnualFeeService.updateById(individualEnterpriseAnnualFee));
	}

	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入IndividualEnterpriseAnnualFee")
	public R submit(@Valid @RequestBody IndividualEnterpriseAnnualFeeEntity individualEnterpriseAnnualFee) {
		return R.status(individualEnterpriseAnnualFeeService.saveOrUpdate(individualEnterpriseAnnualFee));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(individualEnterpriseAnnualFeeService.removeByIds(Func.toLongList(ids)));
	}

}
