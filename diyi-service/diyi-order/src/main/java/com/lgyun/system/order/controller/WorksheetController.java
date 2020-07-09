package com.lgyun.system.order.controller;

import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.system.order.dto.ReleaseWorksheetDTO;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.service.IWorksheetService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.common.api.R;
import lombok.RequiredArgsConstructor;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 工单
 *
 * @author jun
 * @since 2020-07-07 14:40:21
 */
@RestController
@RequestMapping("/order/worksheet")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "工单相关接口", tags = "工单相关接口")
public class WorksheetController {
	private Logger logger = LoggerFactory.getLogger(WorksheetController.class);

	private final IWorksheetService worksheetService;

	private final IMakerService makerService;

	private final IWorksheetMakerService worksheetMakerService;

	/**
	 * 发布工单
	 */
	@PostMapping("/releaseWorksheet")
	@ApiOperation(value = "发布工单", notes = "发布工单")
	public R releaseWorksheet(@Valid @RequestBody ReleaseWorksheetDTO releaseWorksheetDTO) {
		return worksheetService.releaseWorksheet(releaseWorksheetDTO);
	}

	/**
	 * 通过创客名字查询创客
	 */
	@GetMapping("/findNamePage")
	@ApiOperation(value = "通过创客名字查询创客", notes = "通过创客名字查询创客")
	public R findNamePage(String name, Query query) {
		return makerService.findNamePage(Condition.getPage(query),name);
	}

	/**
	 * 抢单
	 */
	@PostMapping("/orderGrabbing")
	@ApiOperation(value = "抢单", notes = "抢单")
	public R orderGrabbing(Long worksheetId,Long makerId) {
		return worksheetService.orderGrabbing(worksheetId,makerId);
	}

	/**
	 * 小程查询工单
	 */
	@GetMapping("/findXiaoPage")
	@ApiOperation(value = "小程查询工单", notes = "小程查询工单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "worksheetState", value = "工单状态：1代表待抢单，2已接单，3已交付", paramType = "query", dataType = "string"),
	})
	public R findXiaoPage(Query query, Integer worksheetState) {
		if(worksheetState != 1 && worksheetState != 2 && worksheetState != 3){
			return R.fail("参数错误");
		}
		return worksheetService.findXiaoPage(Condition.getPage(query),worksheetState);
	}

	/**
	 * 提交工作成果
	 */
	@PostMapping("/submitachievement")
	@ApiOperation(value = "提交工作成果", notes = "提交工作成果")
	public R submitachievement(Long worksheetMakerId,String achievementDesc,String achievementFiles) {
		return worksheetMakerService.submitAchievement(worksheetMakerId,achievementDesc,achievementFiles);
	}

	/**
	 * 验收工作成果
	 */
	@PostMapping("/checkAchievement")
	@ApiOperation(value = "验收工作成果", notes = "验收工作成果")
	public R checkAchievement(Long worksheetMakerId, BigDecimal checkMoney, Long enterpriseId,Boolean bool) {
		return worksheetMakerService.checkAchievement(worksheetMakerId,checkMoney,enterpriseId,bool);
	}

}
