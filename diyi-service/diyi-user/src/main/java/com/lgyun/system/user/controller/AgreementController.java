package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.service.IAgreementService;
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
import java.util.List;

/**
 * Agreement 控制器
 *
 * @author liangfeihu
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

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody AgreementEntity agreement) {
		return R.status(agreementService.save(agreement));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody AgreementEntity agreement) {
		return R.status(agreementService.updateById(agreement));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R detail(Long agreementId) {
		AgreementEntity detail = agreementService.getById(agreementId);
		return R.data(AgreementWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R list(AgreementEntity agreement, Query query) {
		IPage<AgreementEntity> pages = agreementService.page(Condition.getPage(query), Condition.getQueryWrapper(agreement));
		return R.data(AgreementWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(agreementService.removeByIds(Func.toLongList(ids)));
	}

	@GetMapping("/makerIdFind")
	@ApiOperation(value = "根据创客找合同", notes = "根据创客找合同")
	public R makerIdFind(Long makerId) {
		List<AgreementEntity> agreementEntities = agreementService.makerIdFind(makerId);
		return R.data(agreementEntities);
	}

	@GetMapping("/makerIdCompanyFind")
	@ApiOperation(value = "根据创客和商户找合同", notes = "根据创客和商户找合同")
	public R makerIdCompanyFind(Long makerId,Long employeeId) {
		List<AgreementEntity> agreementEntities = agreementService.makerIdCompanyFind(makerId,employeeId);
		return R.data(agreementEntities);
	}

}
