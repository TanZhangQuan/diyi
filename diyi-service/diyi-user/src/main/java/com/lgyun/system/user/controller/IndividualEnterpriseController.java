package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.vo.IndividualEnterpriseVO;
import com.lgyun.system.user.wrapper.IndividualEnterpriseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/individual_enterprise")
@Validated
@AllArgsConstructor
@Api(value = "个独相关接口", tags = "个独相关接口")
public class IndividualEnterpriseController {
	private static Logger logger = LoggerFactory.getLogger(IndividualEnterpriseController.class);

	private IIndividualEnterpriseService individualEnterpriseService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody IndividualEnterpriseAddDto individualEnterpriseAddDto, BladeUser bladeUser) {

		logger.info("新增个体户");
		try {
			return individualEnterpriseService.save(individualEnterpriseAddDto, bladeUser);
		} catch (Exception e) {
			logger.error("新增个体户异常", e);
		}
		return R.fail("新增个体户失败");

	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody IndividualEnterpriseEntity individualEnterprise) {
		return R.status(individualEnterpriseService.updateById(individualEnterprise));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<IndividualEnterpriseVO> detail(IndividualEnterpriseEntity individualEnterprise) {
		IndividualEnterpriseEntity detail = individualEnterpriseService.getOne(Condition.getQueryWrapper(individualEnterprise));
		return R.data(IndividualEnterpriseWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<IndividualEnterpriseVO>> list(IndividualEnterpriseEntity individualEnterprise, Query query) {
		IPage<IndividualEnterpriseEntity> pages = individualEnterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(individualEnterprise));
		return R.data(IndividualEnterpriseWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(individualEnterpriseService.removeByIds(Func.toLongList(ids)));
	}

}
