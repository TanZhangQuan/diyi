package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.service.IIndividualBusinessService;
import com.lgyun.system.user.vo.IndividualBusinessVO;
import com.lgyun.system.user.wrapper.IndividualBusinessWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Slf4j
@RestController
@RequestMapping("/individual_business")
@Validated
@AllArgsConstructor
@Api(value = "个体户相关接口", tags = "个体户相关接口")
public class IndividualBusinessController {

	private IIndividualBusinessService individualBusinessService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody IndividualBusinessAddDto individualBusinessAddDto, BladeUser bladeUser) {

		log.info("新增个体户");
		try {
			return individualBusinessService.save(individualBusinessAddDto, bladeUser);
		} catch (Exception e) {
			log.error("新增个体户异常", e);
		}
		return R.fail("新增个体户失败");

	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody IndividualBusinessEntity individualBusiness) {
		return R.status(individualBusinessService.updateById(individualBusiness));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<IndividualBusinessVO> detail(IndividualBusinessEntity individualBusiness) {
		IndividualBusinessEntity detail = individualBusinessService.getOne(Condition.getQueryWrapper(individualBusiness));
		return R.data(IndividualBusinessWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<IndividualBusinessVO>> list(IndividualBusinessEntity individualBusiness, Query query) {
		IPage<IndividualBusinessEntity> pages = individualBusinessService.page(Condition.getPage(query), Condition.getQueryWrapper(individualBusiness));
		return R.data(IndividualBusinessWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(individualBusinessService.removeByIds(Func.toLongList(ids)));
	}

}
