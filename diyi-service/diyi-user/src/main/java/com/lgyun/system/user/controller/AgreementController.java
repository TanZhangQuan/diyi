package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

	private IMakerEnterpriseService makerEnterpriseService;
	private IMakerService iMakerService;

	@GetMapping("/makerIdFind")
	@ApiOperation(value = "根据创客找合同", notes = "根据创客找合同")
	public R makerIdFind(BladeUser bladeUser,Long onlineAgreementTemplateId,Long onlineAgreementNeedSignId) {
		log.info("根据创客找合同");
		try {
			MakerEntity makerEntity = iMakerService.current(bladeUser);
			return agreementService.makerIdFind(makerEntity.getId(),onlineAgreementTemplateId,onlineAgreementNeedSignId);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("根据创客找合同失败");
		}
	}

	@GetMapping("/makerIdCompanyFind")
	@ApiOperation(value = "根据商户找合同", notes = "根据商户找合同")
	public R<List<AgreementEntity>> makerIdCompanyFind(Long employeeId) {
		log.info("根据商户找合同");
		try {
			List<AgreementEntity> agreementEntities = agreementService.makerIdCompanyFind(employeeId);
			return R.data(agreementEntities);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("根据商户找合同失败");
		}
	}

	/**
	 * 查看商户合同
	 */
	@GetMapping("/getEmployeeAgreement")
	@ApiOperation(value = "查看商户合同", notes = "查看商户合同")
	public R getEmployeeIdAgreement(Long agreementId) {
		log.info("查看商户合同");
		try {
			AgreementEntity agreementEntities = agreementService.getById(agreementId);
			if(null == agreementEntities){
				R.data("");
			}
			return R.data(agreementEntities.getOnlineAggrementUrl());
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("查看商户合同失败");
		}
	}


	/**
	 * 确认签字
	 */
	@PostMapping("/saveOnlineAgreementNeedSign")
	@ApiOperation(value = "确认签字", notes = "确认签字")
	public R saveOnlineAgreementNeedSign(BladeUser bladeUser, String signPic, Long onlineAgreementTemplateId,Long onlineAgreementNeedSignId){
		log.info("保存创客的签名");
		try {
			MakerEntity makerEntity = iMakerService.current(bladeUser);
			return onlineSignPicService.saveOnlineSignPic(makerEntity.getId(), ObjectType.MAKERPEOPLE,signPic,onlineAgreementTemplateId,onlineAgreementNeedSignId);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("保存创客的签名失败");
		}
	}

	/**
	 * 查询创客需要签署的授权协议和合同
	 */
	@GetMapping("/getOnlineAgreementNeedSign")
	@ApiOperation(value = "查询创客需要签署的授权协议和合同", notes = "查询创客需要签署的授权协议和合同")
	public R getOnlineAgreementNeedSign(BladeUser bladeUser,@RequestParam(name = "0.合同，1授权") Integer isContract){
		log.info("查询创客需要签署的授权协议和合同");
		try {
			MakerEntity makerEntity = iMakerService.current(bladeUser);
			return onlineAgreementNeedSignService.getOnlineAgreementNeedSign(makerEntity.getId(),isContract);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("查询创客需要签署的授权协议和合同失败");
		}
	}


	/**
	 * 合作商户
	 */
	@GetMapping("/selectMakerEnterprisePage")
	@ApiOperation(value = "查询合作商户", notes = "查询合作商户")
	public R<IPage<MakerEnterpriseRelationVO>> selectMakerEnterprisePage(BladeUser bladeUser, Query query) {
		log.info("查询合作商户");
		try {
			MakerEntity makerEntity = iMakerService.current(bladeUser);
			IPage<MakerEnterpriseRelationVO> pages = makerEnterpriseService.selectMakerEnterprisePage(Condition.getPage(query), makerEntity.getId(),0);
			return R.data(pages);
		} catch (Exception e){
			e.printStackTrace();
			return R.fail("合作商户失败");
		}
	}

	/**
	 * 上传创客视频
	 */
	@PostMapping("/uploadMakerVideo")
	@ApiOperation(value = "上传创客视频", notes = "上传创客视频")
	public R uploadMakerVideo(BladeUser bladeUser, String applyShortVideo){
		log.info("上传创客视频");
		try {
			MakerEntity makerEntity = iMakerService.current(bladeUser);
			return iMakerService.uploadMakerVideo(makerEntity,applyShortVideo);
		}catch (Exception e){
			e.printStackTrace();
			return R.fail("上传创客视频失败");
		}
	}

}
