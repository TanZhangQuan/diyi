package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
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

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody IndividualEnterpriseAddDto individualEnterpriseAddDto, BladeUser bladeUser) {

		log.info("新增个独");
		try {
			//获取当前创客
			MakerEntity makerEntity = iMakerService.current(bladeUser);
			return individualEnterpriseService.save(individualEnterpriseAddDto, makerEntity);
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
		IPage<IndividualEnterpriseEntity> pages = individualEnterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(individualEnterprise));
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
			@ApiImplicitParam(name = "ibstate", value = "工商个体户状态", paramType = "query", dataType = "string"),
	})
	public R listByMaker(Ibstate ibstate, Query query, BladeUser bladeUser) {

		log.info("查询当前创客的所有个独");
		try {
			MakerEntity makerEntity = iMakerService.current(bladeUser);
			return individualEnterpriseService.listByMaker(Condition.getPage(query.setDescs("create_time")), makerEntity.getId(), ibstate);
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
	@ApiOperation(value = "查询个体户月度开票金额和年度开票金额", notes = "查询个体户月度开票金额和年度开票金额")
	public R yearMonthMoney(@ApiParam(value = "个体户编号") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long individualEnterpriseId) {

		log.info("查询个体户月度开票金额和年度开票金额");
		try {
			return individualEnterpriseService.yearMonthMoney(individualEnterpriseId, MakerType.INDIVIDUALBUSINESS);
		} catch (Exception e) {
			log.error("查询个体户月度开票金额和年度开票金额异常", e);
		}
		return R.fail("查询失败");
	}

}
