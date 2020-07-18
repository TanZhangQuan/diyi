package com.lgyun.system.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.entity.WorksheetMakerEntity;
import com.lgyun.system.order.service.IWorksheetMakerService;
import com.lgyun.system.order.vo.IncomeYearMonthVO;
import com.lgyun.system.order.vo.WorksheetMakerVO;
import com.lgyun.system.order.wrapper.WorksheetMakerWrapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 控制器
 *
 * @author liangfeihu
 * @since 2020-07-18 19:44:52
 */
@Slf4j
@RestController
@RequestMapping("/user/worksheetmaker")
@Validated
@AllArgsConstructor
@Api(value = "相关接口", tags = "相关接口")
public class WorksheetMakerController {

	private IWorksheetMakerService worksheetMakerService;
	private IUserClient iUserClient;

//	@PostMapping("/save")
//	@ApiOperation(value = "新增", notes = "新增")
	public R save(@Valid @RequestBody WorksheetMakerEntity worksheetMaker) {
		return R.status(worksheetMakerService.save(worksheetMaker));
	}

//	@PostMapping("/update")
//	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody WorksheetMakerEntity worksheetMaker) {
		return R.status(worksheetMakerService.updateById(worksheetMaker));
	}

//	@GetMapping("/detail")
//	@ApiOperation(value = "详情", notes = "详情")
	public R<WorksheetMakerVO> detail(WorksheetMakerEntity worksheetMaker) {
		WorksheetMakerEntity detail = worksheetMakerService.getOne(Condition.getQueryWrapper(worksheetMaker));
		return R.data(WorksheetMakerWrapper.build().entityVO(detail));
	}

//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "分页")
	public R<IPage<WorksheetMakerVO>> list(WorksheetMakerEntity worksheetMaker, Query query) {
		IPage<WorksheetMakerEntity> pages = worksheetMakerService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(worksheetMaker));
		return R.data(WorksheetMakerWrapper.build().pageVO(pages));
	}

//	@PostMapping("/remove")
//	@ApiOperation(value = "删除", notes = "删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(worksheetMakerService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 根据工单类型，创客类型，年份或月份查询收入
	 */
	@GetMapping("/query-money-by-year-month")
	@ApiOperation(value = "根据工单类型，创客类型，年份或月份查询收入", notes = "根据工单类型，创客类型，年份或月份查询收入")
	public R<IncomeYearMonthVO> queryMoneyByYearMonth(@ApiParam(value = "工单类型") @NotNull(message = "请选择工单类型") @RequestParam(required = false) WorkSheetType worksheetType,
													  @ApiParam(value = "工单创客类型") @NotNull(message = "请选择工单创客类型") @RequestParam(required = false) MakerType makerType,
													  @ApiParam(value = "年份") @NotNull(message = "请输入个体户编号") @RequestParam(required = false) Long year,
													  @ApiParam(value = "月份") @RequestParam(required = false) Long month,
													  BladeUser bladeUser) {
		MakerEntity makerEntity = iUserClient.currentMaker(bladeUser);
		return worksheetMakerService.queryMoneyByYearMonth(worksheetType, makerType, makerEntity.getId(), year, month);
	}

}
