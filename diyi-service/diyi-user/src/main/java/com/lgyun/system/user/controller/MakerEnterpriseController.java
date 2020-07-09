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
import java.util.List;

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


	@GetMapping("/detail")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "difference", value = "类型,1关联，2关注", paramType = "query", dataType = "int")
	})
	@ApiOperation(value = "详情", notes = "详情")
	public R<MakerEnterpriseRelationVO> detail(Long enterpriseId,int difference) {
		MakerEnterpriseRelationVO enterpriseId1 = iEnterpriseService.getEnterpriseId(enterpriseId, difference);
		return R.data(enterpriseId1);
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
	public R<List<MakerEnterpriseRelationVO>> getEnterpriseName(String enterpriseName) {
		List<MakerEnterpriseRelationVO> makerEnterpriseRelationVOs = iEnterpriseService.getEnterpriseName(enterpriseName);
		return R.data(makerEnterpriseRelationVOs,"查询成功");
	}

	@PostMapping("/addOrCancelfollow")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "makerId", value = "创客id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "attribute", value = "1取消，2添加", paramType = "query", dataType = "int")
	})
	@ApiOperation(value = "添加关注或取消关注", notes = "添加关注或取消关注")
	public R addOrCancelfollow(Long enterpriseId,Long makerId,Integer attribute) {
		return makerEnterpriseService.addOrCancelfollow(enterpriseId,makerId,attribute);
	}

}
