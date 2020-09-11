package com.lgyun.system.user.controller;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDto;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
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
 * @author tzq
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

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody IndividualBusinessEnterpriseAddDto individualBusinessEnterpriseAddDto, BladeUser bladeUser) {

		log.info("新增个独");
		try {
			//查询当前创客
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

	@GetMapping("/list-by-maker")
	@ApiOperation(value = "查询当前创客的所有个独", notes = "查询当前创客的所有个独")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ibstate", value = "工商个独状态", paramType = "query", dataType = "string"),
	})
	public R listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {

		log.info("查询当前创客的所有个独");
		try {
			//查询当前创客
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

}
