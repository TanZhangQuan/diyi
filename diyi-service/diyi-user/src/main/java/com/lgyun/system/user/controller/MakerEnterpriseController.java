package com.lgyun.system.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 *  控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@RestController
@RequestMapping("/makerenterprise")
@Validated
@AllArgsConstructor
@Api(value = "创客和外包企业的关联相关接口", tags = "创客和外包企业的关联相关接口")
public class MakerEnterpriseController {

	private IMakerEnterpriseService makerEnterpriseService;
	private IEnterpriseService iEnterpriseService;

	@GetMapping("/detail")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "difference", value = "类型,0关联，1关注", paramType = "query", dataType = "int")
	})
	@ApiOperation(value = "详情", notes = "详情")
	public R<MakerEnterpriseRelationVO> detail(Long enterpriseId,int difference) {
		log.info("商户的详情");
		try {
			MakerEnterpriseRelationVO enterpriseId1 = iEnterpriseService.getEnterpriseId(enterpriseId, difference);
			return R.data(enterpriseId1);
		} catch (Exception e){
			e.printStackTrace();
			return R.fail("商户的详情失败");
		}

	}

	@GetMapping("/selectMakerEnterprisePage")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "makerId", value = "创客id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "relationshipType", value = "类型", paramType = "query", dataType = "int")
	})
	@ApiOperation(value = "查询关联商户和关注商户", notes = "查询关联商户和关注商户")
	public R<IPage<MakerEnterpriseRelationVO>> selectMakerEnterprisePage(Long makerId, Integer relationshipType, Query query) {
		log.info("查询关联商户和关注商户");
		try {
			IPage<MakerEnterpriseRelationVO> pages = makerEnterpriseService.selectMakerEnterprisePage(Condition.getPage(query), makerId,relationshipType);
			return R.data(pages);
		} catch (Exception e){
			e.printStackTrace();
			return R.fail("查询关联商户和关注商户失败");
		}
	}

	@GetMapping("/getEnterpriseName")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "enterpriseName", value = "商户名字", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "通过商户名字查询", notes = "通过商户名字查询")
	public R<List<MakerEnterpriseRelationVO>> getEnterpriseName(String enterpriseName) {
		log.info("通过商户名字查询商户");
		try {
			List<MakerEnterpriseRelationVO> makerEnterpriseRelationVOs = iEnterpriseService.getEnterpriseName(enterpriseName);
			return R.data(makerEnterpriseRelationVOs,"查询成功");
		} catch (Exception e){
			e.printStackTrace();
			return R.fail("通过商户名字查询商户失败");
		}
	}

	@PostMapping("/addOrCancelfollow")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "enterpriseId", value = "商户id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "makerId", value = "创客id", paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "attribute", value = "1取消，2添加", paramType = "query", dataType = "int")
	})
	@ApiOperation(value = "添加关注或取消关注", notes = "添加关注或取消关注")
	public R addOrCancelfollow(Long enterpriseId,Long makerId,Integer attribute) {
		log.info("添加关注或取消关注");
		try {
			return makerEnterpriseService.addOrCancelfollow(enterpriseId,makerId,attribute);
		} catch (Exception e){
			e.printStackTrace();
			return R.fail("添加关注或取消关注失败");
		}

	}

}
