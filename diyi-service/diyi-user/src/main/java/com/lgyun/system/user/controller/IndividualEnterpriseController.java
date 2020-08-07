package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.wrapper.IndividualEnterpriseWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Slf4j
@RestController
@RequestMapping("/individual-enterprise")
@Validated
@AllArgsConstructor
@Api(value = "个独相关接口", tags = "个独相关接口")
public class IndividualEnterpriseController {

	private IIndividualEnterpriseService individualEnterpriseService;
	private IMakerService iMakerService;
	private IEnterpriseWorkerService enterpriseWorkerService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, BladeUser bladeUser) {

		log.info("新增个独");
		try {
			//获取当前创客
			R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
			if (!(result.isSuccess())){
				return result;
			}
			MakerEntity makerEntity = result.getData();

			return individualEnterpriseService.save(individualBusinessEnterpriseAddDto, makerEntity);
		} catch (Exception e) {
			log.error("新增个独异常", e);
		}
		return R.fail("新增个独失败");

	}

//	@PostMapping("/update")
//	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody IndividualEnterpriseEntity individualEnterprise) {
		return R.status(individualEnterpriseService.updateById(individualEnterprise));
	}

//	@GetMapping("/detail")
//	@ApiOperation(value = "详情", notes = "详情")
	public R detail(@ApiParam(value = "个独编号") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {
		IndividualEnterpriseEntity individualEnterprise = new IndividualEnterpriseEntity();
		individualEnterprise.setId(individualEnterpriseId);
		IndividualEnterpriseEntity detail = individualEnterpriseService.getOne(Condition.getQueryWrapper(individualEnterprise));
		return R.data(IndividualEnterpriseWrapper.build().entityVO(detail));
	}

//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "分页")
	public R list(IndividualEnterpriseEntity individualEnterprise, Query query) {
		IPage<IndividualEnterpriseEntity> pages = individualEnterpriseService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(individualEnterprise));
		return R.data(IndividualEnterpriseWrapper.build().pageVO(pages));
	}

//	@PostMapping("/remove")
//	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(individualEnterpriseService.removeByIds(Func.toLongList(ids)));
	}

	@GetMapping("/list-by-maker")
	@ApiOperation(value = "查询当前创客的所有个独", notes = "查询当前创客的所有个独")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ibstate", value = "工商个独状态", paramType = "query", dataType = "string"),
	})
	public R listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {

		log.info("查询当前创客的所有个独");
		try {
			//获取当前创客
			R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
			if (!(result.isSuccess())){
				return result;
			}
			MakerEntity makerEntity = result.getData();

			return individualEnterpriseService.listByMaker(query, makerEntity.getId(), ibstate);
		} catch (Exception e) {
			log.error("查询当前创客的所有个独异常", e);
		}
		return R.fail("查询失败");
	}

	@GetMapping("/get_by_dto_enterprise")
	@ApiOperation(value = "查询当前商户的所有关联创客的所有个独", notes = "查询当前商户的所有关联创客的所有个独")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "individualBusinessEnterpriseId", value = "个体户编号", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "ibname", value = "个体户名称", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "beginDate", value = "注册开始时间", paramType = "query", dataType = "date"),
			@ApiImplicitParam(name = "endDate", value = "注册结束时间", paramType = "query", dataType = "date")
	})
	public R getByDtoEnterprise(@NotNull(message = "请选择个体户状态") @RequestParam(required = false) Ibstate ibstate, IndividualBusinessEnterpriseDto individualBusinessEnterpriseDto, Query query, BladeUser bladeUser) {

		log.info("查询当前商户的所有关联创客的所有个独");
		try {
			//获取当前商户员工
			R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
			if (!(result.isSuccess())){
				return result;
			}
			EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();

			return individualEnterpriseService.getByDtoEnterprise(Condition.getPage(query.setDescs("create_time")), enterpriseWorkerEntity.getEnterpriseId(), ibstate, individualBusinessEnterpriseDto);
		} catch (Exception e) {
			log.error("查询当前商户的所有关联创客的所有个独异常", e);
		}
		return R.fail("查询失败");
	}

	@GetMapping("/find_by_id_enterprise")
	@ApiOperation(value = "查询当前商户的关联创客的个独详情", notes = "查询当前商户的关联创客的个独详情")
	public R findByIdEnterprise(@ApiParam(value = "个独编号") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {

		log.info("查询当前商户的关联创客的个独详情");
		try {
			return individualEnterpriseService.findByIdEnterprise(individualEnterpriseId);
		} catch (Exception e) {
			log.error("查询当前商户的关联创客的个独详情异常", e);
		}
		return R.fail("查询失败");
	}
	
	@GetMapping("/find-by-id")
	@ApiOperation(value = "查询个独详情", notes = "查询个独详情")
	public R findById(@ApiParam(value = "个独编号") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {

		log.info("查询个独详情");
		try {
			return individualEnterpriseService.findById(individualEnterpriseId);
		} catch (Exception e) {
			log.error("查询个独详情异常", e);
		}
		return R.fail("查询失败");
	}

	@GetMapping("/year-month-money")
	@ApiOperation(value = "查询个独月度开票金额和年度开票金额", notes = "查询个独月度开票金额和年度开票金额")
	public R yearMonthMoney(@ApiParam(value = "个独编号") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualEnterpriseId) {

		log.info("查询个独月度开票金额和年度开票金额");
		try {
			return individualEnterpriseService.yearMonthMoney(individualEnterpriseId, InvoicePeopleType.INDIVIDUALENTERPRISE);
		} catch (Exception e) {
			log.error("查询个独月度开票金额和年度开票金额异常", e);
		}
		return R.fail("查询失败");
	}

	@GetMapping("/self_help_invoice_statistics")
	@ApiOperation(value = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额", notes = "查询个独开票次数，月度开票金额，年度开票金额和总开票金额")
	public R selfHelpInvoiceStatistics(@ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualBusinessId) {

		log.info("查询个独开票次数，月度开票金额，年度开票金额和总开票金额");
		try {
			return individualEnterpriseService.selfHelpInvoiceStatistics(individualBusinessId, InvoicePeopleType.INDIVIDUALENTERPRISE);
		} catch (Exception e) {
			log.error("查询个独开票次数，月度开票金额，年度开票金额和总开票金额异常", e);
		}
		return R.fail("查询失败");
	}

	@GetMapping("/self_help_invoice_list")
	@ApiOperation(value = "查询个独开票记录", notes = "查询个独开票记录")
	public R selfHelpInvoiceList(Query query, @ApiParam(value = "个独ID") @NotNull(message = "请输入个独编号") @RequestParam(required = false) Long individualBusinessId) {

		log.info("查询个独开票记录");
		try {
			return individualEnterpriseService.selfHelpInvoiceList(query, individualBusinessId, InvoicePeopleType.INDIVIDUALENTERPRISE);
		} catch (Exception e) {
			log.error("查询个独开票记录异常", e);
		}
		return R.fail("查询失败");
	}

	@PostMapping("/save_by_enterprise")
	@ApiOperation(value = "当前商户申请创建个独", notes = "当前商户申请创建个独")
	public R saveByEnterprise(@Valid @RequestBody IndividualBusinessEnterpriseWebAddDto individualBusinessEnterpriseWebAddDto, BladeUser bladeUser) {

		log.info("当前商户申请创建个独");
		try {
			//获取当前商户员工
			R<EnterpriseWorkerEntity> result = enterpriseWorkerService.currentEnterpriseWorker(bladeUser);
			if (!(result.isSuccess())){
				return result;
			}
			EnterpriseWorkerEntity enterpriseWorkerEntity = result.getData();
			return individualEnterpriseService.saveByEnterprise(individualBusinessEnterpriseWebAddDto, enterpriseWorkerEntity.getEnterpriseId());
		} catch (Exception e) {
			log.error("当前商户申请创建个独异常", e);
		}
		return R.fail("新增个独失败");

	}

}
