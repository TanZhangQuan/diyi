package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IOnlineAgreementNeedSignService;
import com.lgyun.system.user.service.IOnlineSignPicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	private IOnlineSignPicService onlineSignPicService;

	private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;

	@GetMapping("/makerIdFind")
	@ApiOperation(value = "根据创客找合同", notes = "根据创客找合同")
	public R<List<AgreementEntity>> makerIdFind(Long makerId) {
		log.info("根据创客找合同");
		try {
			List<AgreementEntity> agreementEntities = agreementService.makerIdFind(makerId);
			return R.data(agreementEntities);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("根据创客找合同失败");
		}

	}

	@GetMapping("/makerIdCompanyFind")
	@ApiOperation(value = "根据创客和商户找合同", notes = "根据创客和商户找合同")
	public R<List<AgreementEntity>> makerIdCompanyFind(Long makerId,Long employeeId) {
		log.info("根据创客和商户找合同");
		try {
			List<AgreementEntity> agreementEntities = agreementService.makerIdCompanyFind(makerId,employeeId);
			return R.data(agreementEntities);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("根据创客和商户找合同失败");
		}

	}

	/**
	 * 确认签字
	 */
	@PostMapping("/saveOnlineAgreementNeedSign")
	@ApiOperation(value = "确认签字", notes = "确认签字")
	public R saveOnlineAgreementNeedSign(Long makerId,String signPic,Long onlineAgreementTemplateId){
		log.info("保存创客的签名");
		try {
			return onlineSignPicService.saveOnlineSignPic(makerId,1,signPic,onlineAgreementTemplateId);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("保存创客的签名失败");
		}
	}

	/**
	 * 查询创客需要签署的授权协议
	 */
	@GetMapping("/getOnlineAgreementNeedSign")
	@ApiOperation(value = "查询创客需要签署的授权协议", notes = "查询创客需要签署的授权协议")
	public R getOnlineAgreementNeedSign(Long makerId){
		log.info("查询创客需要签署的授权协议");
		try {
			return onlineAgreementNeedSignService.getOnlineAgreementNeedSign(makerId);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("查询创客需要签署的授权协议失败");
		}
	}


}
