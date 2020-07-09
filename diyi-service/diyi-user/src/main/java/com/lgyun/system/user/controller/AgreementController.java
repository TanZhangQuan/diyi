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
