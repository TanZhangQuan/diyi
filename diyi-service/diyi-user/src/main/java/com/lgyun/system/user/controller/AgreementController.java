package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.vo.AgreementVO;
import com.lgyun.system.user.wrapper.AgreementWrapper;
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
 *
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@RestController
@RequestMapping("/agreement")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "平台合同的信息相关接口", tags = "平台合同的信息相关接口")
public class AgreementController {
	private Logger logger = LoggerFactory.getLogger(AgreementController.class);

	private final IAgreementService agreementService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入agreement")
	public R<AgreementVO> detail(AgreementEntity agreement) {
		AgreementEntity detail = agreementService.getOne(Condition.getQueryWrapper(agreement));
		return R.data(AgreementWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入agreement")
	public R<IPage<AgreementVO>> list(AgreementEntity agreement, Query query) {
		IPage<AgreementEntity> pages = agreementService.page(Condition.getPage(query), Condition.getQueryWrapper(agreement));
		return R.data(AgreementWrapper.build().pageVO(pages));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入agreement")
	public R save(@Valid @RequestBody AgreementEntity agreement) {
		return R.status(agreementService.save(agreement));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入agreement")
	public R update(@Valid @RequestBody AgreementEntity agreement) {
		return R.status(agreementService.updateById(agreement));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入Agreement")
	public R submit(@Valid @RequestBody AgreementEntity agreement) {
		return R.status(agreementService.saveOrUpdate(agreement));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(agreementService.removeByIds(Func.toLongList(ids)));
	}

}
