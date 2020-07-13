package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.service.IAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Agreement 控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@RestController
@RequestMapping("/agreement")
@Validated
@AllArgsConstructor
@Api(value = "平台合同的信息相关接口", tags = "平台合同的信息相关接口")
public class AgreementController {

	private IAgreementService agreementService;

	@GetMapping("/makerIdFind")
	@ApiOperation(value = "根据创客找合同", notes = "根据创客找合同")
	public R<List<AgreementEntity>> makerIdFind(Long makerId) {
		List<AgreementEntity> agreementEntities = agreementService.makerIdFind(makerId);
		return R.data(agreementEntities);
	}

	@GetMapping("/makerIdCompanyFind")
	@ApiOperation(value = "根据创客和商户找合同", notes = "根据创客和商户找合同")
	public R<List<AgreementEntity>> makerIdCompanyFind(Long makerId,Long employeeId) {
		List<AgreementEntity> agreementEntities = agreementService.makerIdCompanyFind(makerId,employeeId);
		return R.data(agreementEntities);
	}

}
