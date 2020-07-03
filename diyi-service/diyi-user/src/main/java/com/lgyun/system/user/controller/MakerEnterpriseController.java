package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.MakerEnterpriseVO;
import com.lgyun.system.user.wrapper.MakerEnterpriseWrapper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@RestController
@RequestMapping("/makerenterprise")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "创客和外包企业的关联关系相关接口", tags = "创客和外包企业的关联关系相关接口")
public class MakerEnterpriseController {
	private Logger logger = LoggerFactory.getLogger(MakerEnterpriseController.class);

	private final IMakerEnterpriseService makerEnterpriseService;
	private final IEnterpriseService iEnterpriseService;

	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody MakerEnterpriseEntity makerEnterprise) {
		return R.status(makerEnterpriseService.save(makerEnterprise));
	}

	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody MakerEnterpriseEntity makerEnterprise) {
		return R.status(makerEnterpriseService.updateById(makerEnterprise));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "详情")
	public R<MakerEnterpriseVO> detail(MakerEnterpriseEntity makerEnterprise) {
		MakerEnterpriseEntity detail = makerEnterpriseService.getOne(Condition.getQueryWrapper(makerEnterprise));
		return R.data(MakerEnterpriseWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<MakerEnterpriseVO>> list(MakerEnterpriseEntity makerEnterprise, Query query) {
		IPage<MakerEnterpriseEntity> pages = makerEnterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(makerEnterprise));
		return R.data(MakerEnterpriseWrapper.build().pageVO(pages));
	}

	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(makerEnterpriseService.removeByIds(Func.toLongList(ids)));
	}

	@GetMapping("/selectMakerEnterprisePage")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "makerId", value = "创客id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "relationshipType", value = "类型", paramType = "query", dataType = "int")
	})
	@ApiOperation(value = "查询关联商户和关注商户", notes = "查询关联商户和关注商户")
	public R<IPage<MakerEnterpriseRelationVO>> selectMakerEnterprisePage(Long makerId, Integer relationshipType, Query query) {
		IPage<MakerEnterpriseRelationVO> pages = makerEnterpriseService.selectMakerEnterprisePage(Condition.getPage(query), makerId,relationshipType);
		return R.data(pages);
	}

	@GetMapping("/getEnterpriseName")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "enterpriseName", value = "商户名字", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "通过商户名字查询", notes = "通过商户名字查询")
	public R<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
		return iEnterpriseService.getEnterpriseName(enterpriseName);
	}

	@GetMapping("/addOrCancelfollow")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "markId", value = "创客id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "relationshipType", value = "1取消，2添加", paramType = "query", dataType = "int")
	})
	@ApiOperation(value = "通过商户名字查询", notes = "通过商户名字查询")
	public R addOrCancelfollow(Long enterpriseId,Long markId,Integer relationshipType) {
		return makerEnterpriseService.addOrCancelfollow(enterpriseId,markId,relationshipType);
	}

}
